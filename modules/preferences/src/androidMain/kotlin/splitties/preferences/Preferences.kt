/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import splitties.experimental.ExperimentalSplittiesApi
import splitties.init.appCtx
import splitties.init.directBootCtx
import kotlin.reflect.KProperty

@ExperimentalSplittiesApi
@SuppressLint("CommitPrefEdits")
@Suppress("NOTHING_TO_INLINE")
abstract class Preferences(
    name: String,
    availableAtDirectBoot: Boolean = false,
    mode: Int = Context.MODE_PRIVATE
) {

    protected val prefs: SharedPreferences

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

    protected var editor: SharedPreferences.Editor by ResettableLazy { prefs.edit() }
        private set

    operator fun contains(o: Any) = prefs === o

    protected fun boolPref(
        defaultValue: Boolean = false
    ) = BoolPref(key = PROP_NAME, defaultValue = defaultValue)

    protected fun intPref(defaultValue: Int) = IntPref(key = PROP_NAME, defaultValue = defaultValue)
    protected fun floatPref(
        defaultValue: Float
    ) = FloatPref(key = PROP_NAME, defaultValue = defaultValue)

    protected fun longPref(
        defaultValue: Long
    ) = LongPref(key = PROP_NAME, defaultValue = defaultValue)

    protected fun stringPref(
        defaultValue: String
    ) = StringPref(key = PROP_NAME, defaultValue = defaultValue)

    protected fun stringOrNullPref(
        defaultValue: String? = null
    ) = StringOrNullPref(key = PROP_NAME, defaultValue = defaultValue)

    protected fun stringSetPref(
        defaultValue: Set<String>
    ) = StringSetPref(key = PROP_NAME, defaultValue = defaultValue)

    protected fun stringSetOrNullPref(
        defaultValue: Set<String>? = null
    ) = StringSetOrNullPref(key = PROP_NAME, defaultValue = defaultValue)

    private var isEditing = false
    private var useCommit = false
    private var useCommitForEdit = false

    fun beginEdit(blocking: Boolean = false) {
        useCommitForEdit = blocking
        isEditing = true
    }

    fun endEdit() {
        if (useCommitForEdit) editor.commit() else editor.apply()
        isEditing = false
    }

    fun abortEdit() {
        editor = editor // Invalidates the editor stored in the delegate
        isEditing = false
    }

    private fun SharedPreferences.Editor.attemptApply() {
        if (isEditing) return
        if (useCommit) commit() else apply()
    }

    private inline fun String.real(prop: KProperty<*>) = if (this === PROP_NAME) prop.name else this

    protected inner class BoolPref(val key: String, val defaultValue: Boolean) {
        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Boolean) {
            editor.putBoolean(key.real(prop), value).attemptApply()
        }

        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Boolean {
            return prefs.getBoolean(key.real(prop), defaultValue)
        }
    }

    protected inner class IntPref(val key: String, val defaultValue: Int) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Int {
            return prefs.getInt(key.real(prop), defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Int) {
            editor.putInt(key.real(prop), value).attemptApply()
        }
    }

    protected inner class FloatPref(val key: String, val defaultValue: Float) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Float {
            return prefs.getFloat(key.real(prop), defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Float) {
            editor.putFloat(key.real(prop), value).attemptApply()
        }
    }

    protected inner class LongPref(val key: String, val defaultValue: Long) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Long {
            return prefs.getLong(key.real(prop), defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Long) {
            editor.putLong(key.real(prop), value).attemptApply()
        }
    }

    protected inner class StringPref(val key: String, val defaultValue: String) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): String {
            return prefs.getString(key.real(prop), defaultValue)!!
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: String) {
            editor.putString(key.real(prop), value).attemptApply()
        }
    }

    protected inner class StringOrNullPref(val key: String, val defaultValue: String? = null) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): String? {
            return prefs.getString(key.real(prop), defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: String?) {
            editor.putString(key.real(prop), value).attemptApply()
        }
    }

    protected inner class StringSetPref(val key: String, val defaultValue: Set<String>) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Set<String> {
            return prefs.getStringSet(key.real(prop), defaultValue)!!
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Set<String>) {
            editor.putStringSet(key.real(prop), value).attemptApply()
        }
    }

    protected inner class StringSetOrNullPref(
        val key: String,
        val defaultValue: Set<String>? = null
    ) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Set<String>? {
            return prefs.getStringSet(key.real(prop), defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Set<String>?) {
            editor.putStringSet(key.real(prop), value).attemptApply()
        }
    }

    companion object {
        private const val PROP_NAME = "__splitties__"
    }
}
