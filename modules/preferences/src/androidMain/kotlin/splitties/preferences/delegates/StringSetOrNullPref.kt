/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences.delegates

import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences
import kotlin.reflect.KProperty

@ExperimentalSplittiesApi
class StringSetOrNullPref(
    private val preferences: Preferences,
    val key: String?,
    val defaultValue: Set<String>? = null
) {
    operator fun getValue(thisRef: Preferences, prop: KProperty<*>): Set<String>? {
        return preferences.prefs.getStringSet(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Preferences, prop: KProperty<*>, value: Set<String>?) {
        with(preferences) {
            editor.putStringSet(key ?: prop.name, value).attemptApply()
        }
    }
}
