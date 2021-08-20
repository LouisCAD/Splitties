/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SuspiciousCollectionReassignment")

package splitties.preferences

import platform.Foundation.NSArray
import platform.Foundation.NSString
import platform.Foundation.NSUserDefaults
import splitties.experimental.NonSymmetricalApi
import kotlin.native.concurrent.freeze
import kotlin.native.ref.WeakReference

internal actual fun getPreferencesStorage(
    name: String?,
    androidAvailableAtDirectBoot: Boolean
): PreferencesStorage {
    val userDefaults = name?.let { NSUserDefaults(suiteName = name) }
        ?: NSUserDefaults.standardUserDefaults
    return NSUserDefaultsBackedPreferencesStorage(userDefaults = userDefaults)
}

/**
 * This implementation can be frozen.
 */
internal class NSUserDefaultsBackedPreferencesStorage(
    internal val userDefaults: NSUserDefaults
) : PreferencesStorage {

    @Suppress("UNCHECKED_CAST")
    override fun getAll() = userDefaults.dictionaryRepresentation() as Map<String, *>

    override fun getString(key: String, defValue: String?): String? {
        return if (key in this) userDefaults.stringForKey(key) else defValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String, defValues: Set<String?>?): Set<String?>? {
        return if (key in this) {
            val stringArray = userDefaults.stringArrayForKey(key) as List<String?>?
            stringArray?.toSet()
        } else defValues
    }

    override fun getInt(key: String, defValue: Int): Int {
        return if (key in this) userDefaults.intForKey(key) else defValue
    }

    override fun getLong(key: String, defValue: Long): Long {
        return if (key in this) userDefaults.longForKey(key) else defValue
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return if (key in this) userDefaults.floatForKey(key) else defValue
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return if (key in this) userDefaults.boolForKey(key) else defValue
    }

    override fun contains(key: String): Boolean = userDefaults.objectForKey(key) != null

    override fun edit(): PreferencesEditor = EditorImpl()

    private var changeListeners: Set<WeakReference<OnPreferenceChangeListener>>
            by FrozenDelegate(emptySet())

    init {
        freeze()
    }

    @NonSymmetricalApi
    override fun registerOnSharedPreferenceChangeListener(listener: OnPreferenceChangeListener) {
        changeListeners += WeakReference(listener)
    }

    @NonSymmetricalApi
    override fun unregisterOnSharedPreferenceChangeListener(listener: OnPreferenceChangeListener) {
        val iterator = changeListeners.iterator()
        iterator.forEach {
            if (it.get() === listener) {
                changeListeners -= it
                return
            }
        }
    }

    private inner class EditorImpl : PreferencesEditor {
        private var unCommittedEdits: Map<String, Any?> by FrozenDelegate(emptyMap())
        private var clear by FrozenDelegate(false)

        override fun putString(key: String, value: String?): PreferencesEditor = apply {
            unCommittedEdits += (key to value)
        }

        override fun putStringSet(key: String, values: Set<String?>?): PreferencesEditor {
            return apply { unCommittedEdits += (key to values) }
        }

        override fun putInt(key: String, value: Int): PreferencesEditor = apply {
            unCommittedEdits += (key to value)
        }

        override fun putLong(key: String, value: Long): PreferencesEditor = apply {
            unCommittedEdits += (key to value)
        }

        override fun putFloat(key: String, value: Float): PreferencesEditor = apply {
            unCommittedEdits += (key to value)
        }

        override fun putBoolean(key: String, value: Boolean): PreferencesEditor = apply {
            unCommittedEdits += (key to value)
        }

        override fun remove(key: String): PreferencesEditor = apply {
            unCommittedEdits += (key to this@EditorImpl)
        }

        override fun clear(): PreferencesEditor = apply {
            clear = true
        }

        override fun commit(): Boolean {
            val editedKeys: Set<String> = unCommittedEdits.keys
            if (clear) {
                val allKeys: Set<String> = getAll().keys
                allKeys.forEach { key ->
                    userDefaults.removeObjectForKey(key)
                }
                val removedAndNotReplacedKeys = allKeys - editedKeys
                removedAndNotReplacedKeys.forEach { key ->
                    val iterator = changeListeners.iterator()
                    iterator.forEach {
                        when (val listener = it.get()) {
                            null -> changeListeners -= it
                            else -> {
                                @OptIn(NonSymmetricalApi::class)
                                listener.onSharedPreferenceChanged(
                                    preferencesStorage = this@NSUserDefaultsBackedPreferencesStorage,
                                    key = key
                                )
                            }
                        }
                    }
                }
                clear = false

            }
            editedKeys.forEach { key ->
                when (val value = unCommittedEdits[key]) {
                    this@EditorImpl -> userDefaults.removeObjectForKey(key)
                    is Boolean -> userDefaults.setBool(value = value, forKey = key)
                    is Float -> {
                        val newValue = value.toFloat()
                        userDefaults.setFloat(value = newValue, forKey = key)
                    }
                    is Long -> userDefaults.setLong(value = value, forKey = key)
                    is Int -> userDefaults.setInt(value = value, forKey = key)
                    is Set<*> -> userDefaults.setObject(
                        value = value.toList() as NSArray,
                        forKey = key
                    )
                    is String -> userDefaults.setObject(value = value as NSString, forKey = key)
                    null -> userDefaults.setObject(value = null, forKey = key)
                    else -> throw UnsupportedOperationException("Unexpected value: $value")
                }
                val iterator = changeListeners.iterator()
                iterator.forEach {
                    when (val listener = it.get()) {
                        null -> changeListeners -= it
                        else -> {
                            @OptIn(NonSymmetricalApi::class)
                            listener.onSharedPreferenceChanged(
                                preferencesStorage = this@NSUserDefaultsBackedPreferencesStorage,
                                key = key
                            )
                        }
                    }
                }
            }
            unCommittedEdits = emptyMap()
            return true
        }

        override fun apply() {
            commit()
        }
    }
}
