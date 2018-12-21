package splitties.preferences

import kotlin.reflect.KProperty

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
