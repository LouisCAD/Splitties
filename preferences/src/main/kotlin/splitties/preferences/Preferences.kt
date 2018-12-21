/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import splitties.init.appCtx
import splitties.init.directBootCtx
import kotlin.reflect.KProperty

@SuppressLint("CommitPrefEdits")
@Suppress("NOTHING_TO_INLINE")
abstract class Preferences(
    name: String,
    availableAtDirectBoot: Boolean = false,
    mode: Int = Context.MODE_PRIVATE
) {

    val prefs: SharedPreferences

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

    protected fun boolPref(
        defaultValue: Boolean = false
    ) = BoolPref(key = null, defaultValue = defaultValue)

    protected fun intPref(defaultValue: Int) = IntPref(key = null, defaultValue = defaultValue)
    protected fun floatPref(
        defaultValue: Float
    ) = FloatPref(key = null, defaultValue = defaultValue)

    protected fun longPref(
        defaultValue: Long
    ) = LongPref(key = null, defaultValue = defaultValue)

    protected fun stringPref(
        defaultValue: String
    ) = StringPref(key = null, defaultValue = defaultValue)

    protected fun stringOrNullPref(
        defaultValue: String? = null
    ) = StringOrNullPref(key = null, defaultValue = defaultValue)

    protected fun stringSetPref(
        defaultValue: Set<String>
    ) = StringSetPref(key = null, defaultValue = defaultValue)

    protected fun stringSetOrNullPref(
        defaultValue: Set<String>? = null
    ) = StringSetOrNullPref(this, key = null, defaultValue = defaultValue)

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

    internal fun SharedPreferences.Editor.attemptApply() {
        if (isEditing) return
        if (useCommit) commit() else apply()
    }

    protected inner class BoolPref(val key: String?, val defaultValue: Boolean) {
        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Boolean) {
            editor.putBoolean(key ?: prop.name, value).attemptApply()
        }

        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Boolean {
            return prefs.getBoolean(key ?: prop.name, defaultValue)
        }
    }

    protected inner class IntPref(val key: String?, val defaultValue: Int) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Int {
            return prefs.getInt(key ?: prop.name, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Int) {
            editor.putInt(key ?: prop.name, value).attemptApply()
        }
    }

    protected inner class FloatPref(val key: String?, val defaultValue: Float) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Float {
            return prefs.getFloat(key ?: prop.name, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Float) {
            editor.putFloat(key ?: prop.name, value).attemptApply()
        }
    }

    protected inner class LongPref(val key: String?, val defaultValue: Long) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Long {
            return prefs.getLong(key ?: prop.name, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Long) {
            editor.putLong(key ?: prop.name, value).attemptApply()
        }
    }

    protected inner class StringPref(val key: String?, val defaultValue: String) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): String {
            return prefs.getString(key ?: prop.name, defaultValue)!!
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: String) {
            editor.putString(key ?: prop.name, value).attemptApply()
        }
    }

    protected inner class StringOrNullPref(val key: String?, val defaultValue: String? = null) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): String? {
            return prefs.getString(key ?: prop.name, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: String?) {
            editor.putString(key ?: prop.name, value).attemptApply()
        }
    }

    protected inner class StringSetPref(val key: String?, val defaultValue: Set<String>) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Set<String> {
            return prefs.getStringSet(key ?: prop.name, defaultValue)!!
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Set<String>) {
            editor.putStringSet(key ?: prop.name, value).attemptApply()
        }
    }

    companion object
}
