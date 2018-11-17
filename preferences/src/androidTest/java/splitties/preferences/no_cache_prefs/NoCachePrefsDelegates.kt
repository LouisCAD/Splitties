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

package splitties.preferences.no_cache_prefs

import android.content.SharedPreferences
import kotlin.reflect.KProperty

internal abstract class PBoolPrefField(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: Boolean
) {


    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) =
        prefs.getBoolean(key, defaultValue)

    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }
}

internal abstract class PIntPrefField(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: Int
) {


    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) =
        prefs.getInt(key, defaultValue)

    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
}

internal abstract class PLongPrefField(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: Long
) {


    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) =
        prefs.getLong(key, defaultValue)

    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }
}

internal abstract class PFloatPrefField(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: Float
) {


    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>) =
        prefs.getFloat(key, defaultValue)

    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }
}

internal abstract class PStringPrefField(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: String? = null
) {

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>): String? =
        prefs.getString(key, defaultValue)

    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String?) {
        prefs.edit().putString(key, value).apply()
    }
}

internal abstract class PStringSetPrefField(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: MutableSet<String>? = null
) {

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>): MutableSet<String>? =
        prefs.getStringSet(key, defaultValue)

    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: MutableSet<String>?) {
        prefs.edit().putStringSet(key, value).apply()
    }
}
