/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import platform.Foundation.NSArray
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSString
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDefaultsDidChangeNotification
import kotlin.native.ref.WeakReference

internal actual fun getSharedPreferences(
    name: String,
    availableAtDirectBoot: Boolean
): SharedPreferences = SharedPreferencesImpl(NSUserDefaults(suiteName = name))

/**
 * This implementation doesn't have locks like the Android implementation has because thanks to
 * Kotlin/Native shared XOR mutable policy, only one thread (the owner) can mutate this, making them
 * unnecessary.
 */
private class SharedPreferencesImpl(private val userDefaults: NSUserDefaults) : SharedPreferences {

    @Suppress("UNCHECKED_CAST")
    override fun getAll() = userDefaults.dictionaryRepresentation() as Map<String, *>

    override fun getString(key: String, defValue: String?): String? {
        return if (key in this) userDefaults.stringForKey(key) else defValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        return if (key in this) {
            val stringArray = userDefaults.stringArrayForKey(key) as List<String>?
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

    override fun edit(): SharedPreferencesEditor = EditorImpl()

    private val changeListeners = mutableSetOf<WeakReference<OnSharedPreferenceChangeListener>>()

    override fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        @Suppress("ConstantConditionIf")//TODO: Link with NotificationCenter
        if (false) {
            NSNotificationCenter.defaultCenter.addObserverForName(
                name = NSUserDefaultsDidChangeNotification,
                `object` = null,
                queue = null,
                usingBlock = { TODO() })
        }
        changeListeners.add(WeakReference(listener))
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        val iterator = changeListeners.iterator()
        iterator.forEach {
            if (it.get() == listener) {
                iterator.remove()
                return
            }
        }
    }

    private inner class EditorImpl : SharedPreferencesEditor {
        private val unCommittedEdits = mutableMapOf<String, Any?>()
        private var clear = false

        override fun putString(key: String, value: String?): SharedPreferencesEditor = apply {
            unCommittedEdits[key] = value
        }

        override fun putStringSet(key: String, values: Set<String>?): SharedPreferencesEditor {
            return apply { unCommittedEdits[key] = values }
        }

        override fun putInt(key: String, value: Int): SharedPreferencesEditor = apply {
            unCommittedEdits[key] = value
        }

        override fun putLong(key: String, value: Long): SharedPreferencesEditor = apply {
            unCommittedEdits[key] = value
        }

        override fun putFloat(key: String, value: Float): SharedPreferencesEditor = apply {
            unCommittedEdits[key] = value
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferencesEditor = apply {
            unCommittedEdits[key] = value
        }

        override fun remove(key: String): SharedPreferencesEditor = apply {
            unCommittedEdits[key] = this@EditorImpl
        }

        override fun clear(): SharedPreferencesEditor = apply {
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
                        it.get()?.onSharedPreferenceChanged(this@SharedPreferencesImpl, key)
                            ?: iterator.remove()
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
                    it.get()?.onSharedPreferenceChanged(this@SharedPreferencesImpl, key)
                        ?: iterator.remove()
                }
            }
            unCommittedEdits.clear()
            return true
        }

        override fun apply() {
            commit()
        }
    }
}
