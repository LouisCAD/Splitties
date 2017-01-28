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
@file:Suppress("NOTHING_TO_INLINE")
package xyz.louiscad.preferences

import android.content.SharedPreferences
import kotlin.reflect.KProperty

abstract class BoolPrefField(protected val prefs: SharedPreferences,
                                      protected val key: String,
                                      defaultValue: Boolean) {

    protected var cachedValue = prefs.getBoolean(key, defaultValue)

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cachedValue
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Boolean) {
        cachedValue = value
        prefs.edit().putBoolean(key, value).apply()
    }
}

abstract class IntPrefField(protected val prefs: SharedPreferences,
                                     protected val key: String,
                                     defaultValue: Int) {

    protected var cachedValue = prefs.getInt(key, defaultValue)

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cachedValue
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Int) {
        cachedValue = value
        prefs.edit().putInt(key, value).apply()
    }
}

abstract class LongPrefField(protected val prefs: SharedPreferences,
                                      protected val key: String,
                                      defaultValue: Long) {

    protected var cachedValue = prefs.getLong(key, defaultValue)

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cachedValue
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Long) {
        cachedValue = value
        prefs.edit().putLong(key, value).apply()
    }
}

abstract class FloatPrefField(protected val prefs: SharedPreferences,
                                       protected val key: String,
                                       defaultValue: Float) {

    protected var cachedValue = prefs.getFloat(key, defaultValue)

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cachedValue
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Float) {
        cachedValue = value
        prefs.edit().putFloat(key, value).apply()
    }
}

abstract class StringPrefField(protected val prefs: SharedPreferences,
                                        protected val key: String,
                                        defaultValue: String? = null) {

    protected var cachedValue: String? = prefs.getString(key, defaultValue)

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cachedValue
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String?) {
        cachedValue = value
        prefs.edit().putString(key, value).apply()
    }
}

abstract class StringSetPrefField(protected val prefs: SharedPreferences,
                                           protected val key: String,
                                           defaultValue: MutableSet<String>? = null) {

    protected var cachedValue: MutableSet<String>? = prefs.getStringSet(key, defaultValue)

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cachedValue
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: MutableSet<String>?) {
        cachedValue = value
        prefs.edit().putStringSet(key, value).apply()
    }
}
