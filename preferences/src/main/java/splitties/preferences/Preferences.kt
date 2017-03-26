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

import android.content.Context
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import splitties.init.appCtx
import splitties.init.directBootCtx
import kotlin.reflect.KProperty

@Suppress("NOTHING_TO_INLINE")
abstract class Preferences(name: String, availableAtDirectBoot: Boolean = false, mode: Int = Context.MODE_PRIVATE) {

    protected val prefs: SharedPreferences

    init {
        val storageCtx: Context = if (availableAtDirectBoot && SDK_INT > 24) {
            // Moving the sharedPreferences from is done by the system only if you had it outside
            // the direct boot available storage or if the device was running Android M or older,
            // and just got updated. These two cases are extremely rare, and it's unlikely that your
            // sharedPreferences are big enough to significantly harm the user experience because of
            // the operation happening on UI thread for the single time (per preference file) this
            // move operations will take place.
            // noinspection NewApi
            directBootCtx.moveSharedPreferencesFrom(appCtx, name)
            directBootCtx
        } else appCtx
        prefs = storageCtx.getSharedPreferences(name, mode)
    }

    protected var editor: SharedPreferences.Editor by ResettableLazy { prefs.edit() }
        private set

    operator fun contains(o: Any) = prefs === o

    protected fun boolPref(defaultValue: Boolean = false)
            = BoolPrefProvider(defaultValue = defaultValue)

    protected fun intPref(defaultValue: Int) = IntPrefProvider(defaultValue = defaultValue)
    protected fun floatPref(defaultValue: Float) = FloatPrefProvider(defaultValue = defaultValue)
    protected fun longPref(defaultValue: Long) = LongPrefProvider(defaultValue = defaultValue)
    protected fun stringPref(defaultValue: String? = null)
            = StringPrefProvider(defaultValue = defaultValue)

    protected fun stringSetPref(defaultValue: MutableSet<String>? = null)
            = StringSetPrefProvider(defaultValue = defaultValue)

    private var edit = false
    private var useCommit = false
    private var useCommitForEdit = false

    fun beginEdit(blocking: Boolean = false) {
        useCommitForEdit = blocking
        edit = true
    }

    fun endEdit() {
        if (useCommitForEdit) editor.commit() else editor.apply()
        edit = false
    }

    fun abortEdit() {
        editor = editor // Invalidates the editor stored in the delegate
        edit = false
    }

    private fun SharedPreferences.Editor.attemptApply() {
        if (edit) return
        if (useCommit) commit() else apply()
    }

    protected inner class BoolPref(val key: String, val defaultValue: Boolean) {
        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Boolean) {
            editor.putBoolean(key, value).attemptApply()
        }

        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Boolean {
            return prefs.getBoolean(key, defaultValue)
        }
    }

    protected inner class IntPref(val key: String, val defaultValue: Int) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Int {
            return prefs.getInt(key, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Int) {
            editor.putInt(key, value).attemptApply()
        }
    }

    protected inner class FloatPref(val key: String, val defaultValue: Float) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Float {
            return prefs.getFloat(key, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Float) {
            editor.putFloat(key, value).attemptApply()
        }
    }

    protected inner class LongPref(val key: String, val defaultValue: Long) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Long {
            return prefs.getLong(key, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Long) {
            editor.putLong(key, value).attemptApply()
        }
    }

    protected inner class StringPref(val key: String, val defaultValue: String? = null) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): String? {
            return prefs.getString(key, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: String?) {
            editor.putString(key, value).attemptApply()
        }
    }

    protected inner class StringSetPref(val key: String,
                                        val defaultValue: MutableSet<String>? = null) {
        operator fun getValue(thisRef: Preferences, prop: KProperty<*>): MutableSet<String>? {
            return prefs.getStringSet(key, defaultValue)
        }

        operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: MutableSet<String>?) {
            editor.putStringSet(key, value).attemptApply()
        }
    }

    /**
     * Base class for Preferences delegate providers that need to use the property name as key
     * by default.
     */
    abstract class PrefProvider(private val key: String) {
        protected fun getKey(prop: KProperty<*>) = if (key === PROP_NAME) prop.name else key
    }

    protected inner class BoolPrefProvider(key: String = PROP_NAME, val defaultValue: Boolean)
        : PrefProvider(key) {
        operator fun provideDelegate(thisRef: Preferences, prop: KProperty<*>): BoolPref {
            return BoolPref(getKey(prop), defaultValue)
        }
    }

    protected inner class FloatPrefProvider(key: String = PROP_NAME, val defaultValue: Float)
        : PrefProvider(key) {
        operator fun provideDelegate(thisRef: Preferences, prop: KProperty<*>): FloatPref {
            return FloatPref(getKey(prop), defaultValue)
        }
    }

    protected inner class LongPrefProvider(key: String = PROP_NAME, val defaultValue: Long)
        : PrefProvider(key) {
        operator fun provideDelegate(thisRef: Preferences, prop: KProperty<*>): LongPref {
            return LongPref(getKey(prop), defaultValue)
        }
    }

    protected inner class IntPrefProvider(key: String = PROP_NAME, val defaultValue: Int)
        : PrefProvider(key) {
        operator fun provideDelegate(thisRef: Preferences, prop: KProperty<*>): IntPref {
            return IntPref(getKey(prop), defaultValue)
        }
    }

    protected inner class StringPrefProvider(key: String = PROP_NAME,
                                             val defaultValue: String? = null)
        : PrefProvider(key) {
        operator fun provideDelegate(thisRef: Preferences, prop: KProperty<*>): StringPref {
            return StringPref(getKey(prop), defaultValue)
        }
    }

    protected inner class StringSetPrefProvider(key: String = PROP_NAME,
                                                val defaultValue: MutableSet<String>? = null)
        : PrefProvider(key) {
        operator fun provideDelegate(thisRef: Preferences, prop: KProperty<*>): StringSetPref {
            return StringSetPref(getKey(prop), defaultValue)
        }
    }

    companion object {
        private val PROP_NAME = "__splitties__"
    }
}
