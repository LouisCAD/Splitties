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

package com.actinarium.nagbox.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.actinarium.nagbox.R;
import com.actinarium.nagbox.databinding.EditTaskDialogBinding;
import com.actinarium.nagbox.model.Task;

/**
 * A popup window for creating and editing tasks, implemented as a dialog fragment
 *
 * @author Paul Danyliuk
 */
@SuppressWarnings("ConstantConditions")
public class EditTaskDialogFragment extends DialogFragment {

    public static final String TAG = "EditTaskDialogFragment";

    private static final String ARG_TASK = "com.actinarium.nagbox.arg.TASK";

    private Host mHost;
    /**
     * Task bound to the form, i.e. the one to save
     */
    private EditTaskDialogBinding mBinding;
    private Task mTask;

    /**
     * Create a new instance of the dialog fragment, either to create or edit a task
     *
     * @param taskToEdit task to edit, or <code>null</code> to make a dialog for creating a new task
     * @return dialog fragment instance
     */
    public static EditTaskDialogFragment newInstance(@Nullable Task taskToEdit) {
        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
        Bundle args = new Bundle(1);
        args.putParcelable(ARG_TASK, taskToEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHost = (Host) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity " + context + " must implement EditTaskDialogFragment.Host", e);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Task taskToEdit = getArguments().getParcelable(ARG_TASK);
        final boolean isEdit = taskToEdit != null;

        // Set up our live model:
        if (savedInstanceState != null) {
            // a) restore after config change
            mTask = savedInstanceState.getParcelable(ARG_TASK);
        } else if (isEdit) {
            // b) populate from the one under editing
            mTask = new Task(taskToEdit);
        } else {
            // c) create a blank task with default interval = 5 mins
            mTask = new Task();
        }

        mBinding = EditTaskDialogBinding.inflate(LayoutInflater.from(getContext()), null, false);
        mBinding.setTask(mTask);

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(isEdit ? R.string.dialog_edit_task : R.string.dialog_new_task))
                .setView(mBinding.getRoot())
                .setPositiveButton(R.string.dialog_save, null)
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create();

        // At this moment dialog is not yet inflated, so we need to use this listener to defer some logic
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface di) {
                // Set the positive button listener. This is needed to prevent dismissing the dialog when validation fails
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isValid = validateForm();
                        if (isValid) {
                            // Actually I could call the service right here instead of delegating it to the activity
                            // But for the sake of consistency let the activity serve as a controller and handle this
                            if (isEdit) {
                                mHost.saveEditedTask(mTask);
                            } else {
                                mHost.saveNewTask(mTask);
                            }
                            dismiss();
                        }
                    }
                });

                // Also try requesting the input method
                if (mBinding.taskTitleInput.getEditText().requestFocus()) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mBinding.taskTitleInput.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_TASK, mTask);
    }

    /**
     * Perform validation when save is clicked. Show validation errors if needed.
     *
     * @return true if validation passed, false if not
     */
    private boolean validateForm() {
        boolean isTitleError = false;
        boolean isIntervalError = false;
        if (mTask.title == null || mTask.title.trim().isEmpty()) {
            mBinding.taskTitleInput.setError(getString(R.string.dialog_empty_title));
            isTitleError = true;
        }
        if (mTask.interval <= 0) {
            mBinding.taskIntervalInput.setError(getString(R.string.dialog_invalid_interval));
            isIntervalError = true;
        }
        mBinding.taskTitleInput.setErrorEnabled(isTitleError);
        mBinding.taskIntervalInput.setErrorEnabled(isIntervalError);

        if (isTitleError || isIntervalError) {
            return false;
        }
        // Otherwise we're fine
        return true;
    }

    /**
     * Callbacks to the controller (i.e. activity)
     */
    public interface Host {
        void saveNewTask(Task task);
        void saveEditedTask(Task task);
    }
}
