/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowViaChannel
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * This class doesn't extend [ReadWriteProperty] to avoid double autoboxing on primitive values.
 */
sealed class PrefDelegate<T>(
    private val preferences: Preferences,
    val key: String?
) {
    @ExperimentalSplittiesApi
    fun doesBelongTo(sharedPreferences: SharedPreferences): Boolean {
        val delegateBackingPreferences: SharedPreferences = preferences.prefs
        return delegateBackingPreferences === sharedPreferences
    }

    @FlowPreview
    @ExperimentalSplittiesApi
    fun changesFlow(): Flow<Unit> {
        if (key == null) throw UnsupportedOperationException(
            "To use changesFlow, you need to " +
                    "specify the key of the preference item explicitly."
        )
        return flowViaChannel(Channel.CONFLATED) { sendChannel ->
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
                if (key == changedKey) runCatching { sendChannel.offer(Unit) }
            }
            preferences.prefs.registerOnSharedPreferenceChangeListener(listener)
            @Suppress("EXPERIMENTAL_API_USAGE")
            sendChannel.invokeOnClose {
                preferences.prefs.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }
    }

    @FlowPreview
    fun valueFlow(): Flow<T> {
        if (key == null) throw UnsupportedOperationException(
            "To use valueFlow, you need to " +
                    "specify the key of the preference item explicitly."
        )
        return flowViaChannel<T>(Channel.CONFLATED) { sendChannel ->
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
                if (key == changedKey) runCatching {
                    sendChannel.offer(getValue())
                }
            }
            preferences.prefs.registerOnSharedPreferenceChangeListener(listener)
            runCatching {
                sendChannel.offer(getValue())
            }
            @Suppress("EXPERIMENTAL_API_USAGE")
            sendChannel.invokeOnClose {
                preferences.prefs.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }.distinctUntilChanged()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    private fun getValue(): T = when (this) {
        is BoolPref -> preferences.prefs.getBoolean(key!!, defaultValue)
        is IntPref -> preferences.prefs.getInt(key!!, defaultValue)
        is FloatPref -> preferences.prefs.getFloat(key!!, defaultValue)
        is LongPref -> preferences.prefs.getLong(key!!, defaultValue)
        is StringPref -> preferences.prefs.getString(key!!, defaultValue)!!
        is StringOrNullPref -> preferences.prefs.getString(key!!, defaultValue)
        is StringSetPref -> preferences.prefs.getStringSet(key!!, defaultValue)
        is StringSetOrNullPref -> preferences.prefs.getStringSet(key!!, defaultValue)!!
    } as T
}

class BoolPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: Boolean
) : PrefDelegate<Boolean>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Boolean {
        return preferences.prefs.getBoolean(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Boolean) {
        with(preferences) {
            editor.putBoolean(key ?: prop.name, value).attemptApply()
        }
    }
}

class IntPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: Int
) : PrefDelegate<Int>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Int {
        return preferences.prefs.getInt(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Int) {
        with(preferences) {
            editor.putInt(key ?: prop.name, value).attemptApply()
        }
    }
}

class FloatPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: Float
) : PrefDelegate<Float>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Float {
        return preferences.prefs.getFloat(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Float) {
        with(preferences) {
            editor.putFloat(key ?: prop.name, value).attemptApply()
        }
    }
}

class LongPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: Long
) : PrefDelegate<Long>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Long {
        return preferences.prefs.getLong(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Long) {
        with(preferences) {
            editor.putLong(key ?: prop.name, value).attemptApply()
        }
    }
}

class StringPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: String
) : PrefDelegate<String>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return preferences.prefs.getString(key ?: prop.name, defaultValue)!!
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
        with(preferences) {
            editor.putString(key ?: prop.name, value).attemptApply()
        }
    }
}

class StringOrNullPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: String?
) : PrefDelegate<String?>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String? {
        return preferences.prefs.getString(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String?) {
        with(preferences) {
            editor.putString(key ?: prop.name, value).attemptApply()
        }
    }
}

class StringSetPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: Set<String> = emptySet()
) : PrefDelegate<Set<String>>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Set<String> {
        return preferences.prefs.getStringSet(key ?: prop.name, defaultValue)!!
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Set<String>) {
        with(preferences) {
            editor.putStringSet(key ?: prop.name, value).attemptApply()
        }
    }
}

class StringSetOrNullPref @PublishedApi internal constructor(
    private val preferences: Preferences,
    key: String?,
    val defaultValue: Set<String>? = null
) : PrefDelegate<Set<String>?>(preferences, key) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Set<String>? {
        return preferences.prefs.getStringSet(key ?: prop.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Set<String>?) {
        with(preferences) {
            editor.putStringSet(key ?: prop.name, value).attemptApply()
        }
    }
}
