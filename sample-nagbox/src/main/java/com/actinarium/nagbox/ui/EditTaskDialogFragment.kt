/*
 * Copyright (C) 2016 Actinarium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actinarium.nagbox.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import com.actinarium.nagbox.R
import com.actinarium.nagbox.databinding.EditTaskDialogBinding
import com.actinarium.nagbox.model.Task

/**
 * A popup window for creating and editing tasks, implemented as a dialog fragment

 * @author Paul Danyliuk
 */
class EditTaskDialogFragment : DialogFragment() {

    private var mHost: Host? = null
    /**
     * Task bound to the form, i.e. the one to save
     */
    private var mBinding: EditTaskDialogBinding? = null
    private lateinit var mTask: Task

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mHost = context as Host?
        } catch (e: ClassCastException) {
            throw RuntimeException("Activity $context must implement EditTaskDialogFragment.Host", e)
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val taskToEdit = arguments.getParcelable<Task>(ARG_TASK)
        val isEdit = taskToEdit != null

        // Set up our live model:
        if (savedInstanceState != null) {
            // a) restore after config change
            mTask = savedInstanceState.getParcelable<Task>(ARG_TASK)
        } else if (isEdit) {
            // b) populate from the one under editing
            mTask = taskToEdit.copy()
        } else {
            // c) create a blank task with default interval = 5 mins
            mTask = Task()
        }

        mBinding = EditTaskDialogBinding.inflate(LayoutInflater.from(context), null, false)
        mBinding!!.task = mTask

        val dialog = AlertDialog.Builder(context)
                .setTitle(getString(if (isEdit) R.string.dialog_edit_task else R.string.dialog_new_task))
                .setView(mBinding!!.root)
                .setPositiveButton(R.string.dialog_save, null)
                .setNegativeButton(R.string.dialog_cancel) { dialogInterface, i -> dialogInterface.cancel() }
                .create()

        // At this moment dialog is not yet inflated, so we need to use this listener to defer some logic
        dialog.setOnShowListener {
            // Set the positive button listener. This is needed to prevent dismissing the dialog when validation fails
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val isValid = validateForm()
                if (isValid) {
                    // Actually I could call the service right here instead of delegating it to the activity
                    // But for the sake of consistency let the activity serve as a controller and handle this
                    if (isEdit) {
                        mHost!!.saveEditedTask(mTask)
                    } else {
                        mHost!!.saveNewTask(mTask)
                    }
                    dismiss()
                }
            }

            // Also try requesting the input method
            if (mBinding!!.taskTitleInput.editText!!.requestFocus()) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(mBinding!!.taskTitleInput.editText, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        return dialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_TASK, mTask)
    }

    /**
     * Perform validation when save is clicked. Show validation errors if needed.

     * @return true if validation passed, false if not
     */
    private fun validateForm(): Boolean {
        var isTitleError = false
        var isIntervalError = false
        if (mTask!!.title == null || mTask!!.title.trim { it <= ' ' }.isEmpty()) {
            mBinding!!.taskTitleInput.error = getString(R.string.dialog_empty_title)
            isTitleError = true
        }
        if (mTask!!.interval <= 0) {
            mBinding!!.taskIntervalInput.error = getString(R.string.dialog_invalid_interval)
            isIntervalError = true
        }
        mBinding!!.taskTitleInput.isErrorEnabled = isTitleError
        mBinding!!.taskIntervalInput.isErrorEnabled = isIntervalError

        if (isTitleError || isIntervalError) {
            return false
        }
        // Otherwise we're fine
        return true
    }

    /**
     * Callbacks to the controller (i.e. activity)
     */
    interface Host {
        fun saveNewTask(task: Task)
        fun saveEditedTask(task: Task)
    }

    companion object {

        val TAG = "EditTaskDialogFragment"

        private val ARG_TASK = "com.actinarium.nagbox.arg.TASK"

        /**
         * Create a new instance of the dialog fragment, either to create or edit a task

         * @param taskToEdit task to edit, or `null` to make a dialog for creating a new task
         * *
         * @return dialog fragment instance
         */
        fun newInstance(taskToEdit: Task?): EditTaskDialogFragment {
            val fragment = EditTaskDialogFragment()
            val args = Bundle(1)
            args.putParcelable(ARG_TASK, taskToEdit)
            fragment.arguments = args
            return fragment
        }
    }
}
