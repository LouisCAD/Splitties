/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import splitties.experimental.InternalSplittiesApi
import splitties.init.appCtx
import splitties.init.directBootCtx

@SuppressLint("CommitPrefEdits")
abstract class PreferencesBase internal constructor(
    name: String,
    availableAtDirectBoot: Boolean = false,
    mode: Int = Context.MODE_PRIVATE
) {

    val prefs: SharedPreferences

    @InternalSplittiesApi
    fun beginEdit(blocking: Boolean = false) {
        useCommitForEdit = blocking
        isEditing = true
    }

    @InternalSplittiesApi
    fun endEdit() {
        if (useCommitForEdit) editor.commit() else editor.apply()
        isEditing = false
    }

    @InternalSplittiesApi
    fun abortEdit() {
        editor = editor // Invalidates the editor stored in the delegate
        isEditing = false
    }

    init {
        val storageCtx: Context = if (availableAtDirectBoot && SDK_INT > 24) {
            // Moving the sharedPreferences from is done by the system only if you had it outside
            // the direct boot available storage or if the device was running Android M or older,
            // and just got updated.
            directBootCtx.moveSharedPreferencesFrom(appCtx, name)
            directBootCtx
        } else appCtx
        prefs = storageCtx.getSharedPreferences(name, mode)
    }

    internal var editor: SharedPreferences.Editor by ResettableLazy { prefs.edit() }
        private set

    operator fun contains(o: Any) = prefs === o

    private var isEditing = false
    private var useCommitForEdit = false

    internal fun SharedPreferences.Editor.attemptApply() {
        if (isEditing) return
        apply()
    }
}
