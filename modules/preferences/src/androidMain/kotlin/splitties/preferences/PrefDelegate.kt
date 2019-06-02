/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

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
    @JvmField protected val preferences: Preferences,
    @JvmField val key: String
) {
    @ExperimentalSplittiesApi
    fun doesBelongTo(sharedPreferences: SharedPreferences): Boolean {
        val delegateBackingPreferences: SharedPreferences = preferences.prefs
        return delegateBackingPreferences === sharedPreferences
    }

    @FlowPreview
    @ExperimentalSplittiesApi
    fun changesFlow(): Flow<Unit> = flowViaChannel(Channel.CONFLATED) { sendChannel ->
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (key == changedKey) runCatching { sendChannel.offer(Unit) }
        }
        preferences.prefs.registerOnSharedPreferenceChangeListener(listener)
        @Suppress("EXPERIMENTAL_API_USAGE")
        sendChannel.invokeOnClose {
            preferences.prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    @FlowPreview
    fun valueFlow(): Flow<T> = flowViaChannel<T>(Channel.CONFLATED) { sendChannel ->
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

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    private fun getValue(): T = when (this) {
        is BoolPref -> preferences.prefs.getBoolean(key, defaultValue)
        is IntPref -> preferences.prefs.getInt(key, defaultValue)
        is FloatPref -> preferences.prefs.getFloat(key, defaultValue)
        is LongPref -> preferences.prefs.getLong(key, defaultValue)
        is StringPref -> preferences.prefs.getString(key, defaultValue)!!
        is StringOrNullPref -> preferences.prefs.getString(key, defaultValue)
        is StringSetPref -> preferences.prefs.getStringSet(key, defaultValue)
        is StringSetOrNullPref -> preferences.prefs.getStringSet(key, defaultValue)!!
    } as T
}

class BoolPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: Boolean
) : PrefDelegate<Boolean>(preferences, key) {

    var value: Boolean
        get() = preferences.prefs.getBoolean(key, defaultValue)
        set(value) = with(preferences) {
            editor.putBoolean(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): Boolean = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: Boolean) {
        this.value = value
    }
}

class IntPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: Int
) : PrefDelegate<Int>(preferences, key) {

    var value: Int
        get() = preferences.prefs.getInt(key, defaultValue)
        set(value) = with(preferences) {
            editor.putInt(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): Int = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: Int) {
        this.value = value
    }
}

class FloatPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: Float
) : PrefDelegate<Float>(preferences, key) {

    var value: Float
        get() = preferences.prefs.getFloat(key, defaultValue)
        set(value) = with(preferences) {
            editor.putFloat(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): Float = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: Float) {
        this.value = value
    }
}

class LongPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: Long
) : PrefDelegate<Long>(preferences, key) {

    var value: Long
        get() = preferences.prefs.getLong(key, defaultValue)
        set(value) = with(preferences) {
            editor.putLong(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): Long = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: Long) {
        this.value = value
    }
}

class StringPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: String
) : PrefDelegate<String>(preferences, key) {

    var value: String
        get() = preferences.prefs.getString(key, defaultValue)!!
        set(value) = with(preferences) {
            editor.putString(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): String = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: String) {
        this.value = value
    }
}

class StringOrNullPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: String?
) : PrefDelegate<String?>(preferences, key) {

    var value: String?
        get() = preferences.prefs.getString(key, defaultValue)
        set(value) = with(preferences) {
            editor.putString(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): String? = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: String?) {
        this.value = value
    }
}

class StringSetPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: Set<String>
) : PrefDelegate<Set<String>>(preferences, key) {

    var value: Set<String>
        get() = preferences.prefs.getStringSet(key, defaultValue)!!
        set(value) = with(preferences) {
            editor.putStringSet(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): Set<String> = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: Set<String>) {
        this.value = value
    }
}

class StringSetOrNullPref @PublishedApi internal constructor(
    preferences: Preferences,
    key: String,
    val defaultValue: Set<String>?
) : PrefDelegate<Set<String>?>(preferences, key) {

    var value: Set<String?>
        get() = preferences.prefs.getStringSet(key, defaultValue)!!
        set(value) = with(preferences) {
            editor.putStringSet(key, value).attemptApply()
        }

    inline operator fun getValue(thisRef: Any?, prop: KProperty<*>?): Set<String?> = value
    inline operator fun setValue(thisRef: Any?, prop: KProperty<*>?, value: Set<String?>) {
        this.value = value
    }
}
