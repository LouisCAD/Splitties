/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SuspiciousCollectionReassignment")

package splitties.preferences

import android.content.Context
import android.content.SharedPreferences.Editor
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import splitties.experimental.NonSymmetricalApi
import splitties.init.appCtx
import splitties.init.directBootCtx
import splitties.mainthread.checkNotMainThread
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

internal fun getDataStoreBackedSharedPreferences(
    name: String?,
    androidAvailableAtDirectBoot: Boolean
): PreferencesStorage {
    checkNotMainThread()
    val actualName = name ?: "${appCtx.packageName}_preferences"
    val storageCtx: Context = if (androidAvailableAtDirectBoot && Build.VERSION.SDK_INT > 24) {
        // Moving the sharedPreferences from is done by the system only if you had it outside
        // the direct boot available storage or if the device was running Android M or older,
        // and just got updated.
        directBootCtx.moveSharedPreferencesFrom(appCtx, actualName)
        directBootCtx
    } else appCtx
    val dataStore: DataStore<Preferences>
    val data = runBlocking {
        val holder = object : Any() {
            val Context.dataStore by preferencesDataStore(
                name = actualName,
                produceMigrations = {
                    listOf(SharedPreferencesMigration(context = it, sharedPreferencesName = actualName))
                }
            )
        }
        dataStore = with(holder) { storageCtx.dataStore }
        dataStore.data.stateIn(CoroutineScope(Dispatchers.Default))
    }
    return DataStorePreferences(dataStore, data)
}

internal class DataStorePreferences(
    private val dataStore: DataStore<Preferences>,
    private val dataFlow: StateFlow<Preferences>
) : PreferencesStorage {

    private val latestData: Preferences by dataFlow::value

    override fun getAll() = latestData.asMap().mapKeys { it.key.name }

    override fun getString(key: String, defValue: String?): String? {
        return latestData[stringPreferencesKey(name = key)] ?: defValue
    }

    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        return latestData[stringSetPreferencesKey(name = key)] ?: defValues
    }

    override fun getInt(key: String, defValue: Int): Int {
        return latestData[intPreferencesKey(name = key)] ?: defValue
    }

    override fun getLong(key: String, defValue: Long): Long {
        return latestData[longPreferencesKey(name = key)] ?: defValue
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return latestData[floatPreferencesKey(name = key)] ?: defValue
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return latestData[booleanPreferencesKey(name = key)] ?: defValue
    }

    override fun contains(key: String) = latestData.asMap().any { it.key.name == key }

    override fun edit(): Editor = EditorImpl()

    private var changeListeners: Set<WeakReference<OnSharedPreferenceChangeListener>> = emptySet()

    override fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        changeListeners += WeakReference(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        val iterator = changeListeners.iterator()
        iterator.forEach {
            if (it.get() === listener) {
                changeListeners -= it
                return
            }
        }
    }

    private val updatesScope = CoroutineScope(Dispatchers.Default)

    private inner class EditorImpl : PreferencesEditor {
        private val unCommittedEdits: MutableMap<String, Any?> = ConcurrentHashMap()
        private var clear = false

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
            checkNotMainThread()
            runBlocking { performEdit() }
            return true
        }

        override fun apply() {
            updatesScope.launch {
                performEdit()
            }
        }

        private fun MutablePreferences.removeForKey(key: String) {
            remove(stringPreferencesKey(key)) // The type of the key is erased, so it doesn't matter.
        }

        private suspend fun performEdit() {
            lateinit var keysToNotifyForChange: Set<String>
            val editedKeys: Set<String> = unCommittedEdits.keys.toSet()
            dataStore.edit { prefs ->
                keysToNotifyForChange = if (clear) {
                    val allKeys: Set<String> = getAll().keys
                    allKeys.forEach { keyName ->
                        prefs.removeForKey(keyName)
                    }
                    clear = false
                    allKeys
                } else editedKeys
                editedKeys.forEach { key ->
                    when (val value = unCommittedEdits[key]) {
                        this@EditorImpl -> prefs.removeForKey(key)
                        is Boolean -> prefs[booleanPreferencesKey(key)] = value
                        is Float -> prefs[floatPreferencesKey(key)] = value
                        is Long -> prefs[longPreferencesKey(key)] = value
                        is Int -> prefs[intPreferencesKey(key)] = value
                        is Set<*> -> {
                            @Suppress("unchecked_cast")
                            prefs[stringSetPreferencesKey(key)] = value as Set<String>
                        }
                        is String -> prefs[stringPreferencesKey(key)] = value
                        null -> prefs.removeForKey(key)
                        else -> throw UnsupportedOperationException("Unexpected value: $value")
                    }
                }
                unCommittedEdits.clear()
            }
            keysToNotifyForChange.forEach { key ->
                val iterator = changeListeners.iterator()
                iterator.forEach {
                    when (val listener = it.get()) {
                        null -> changeListeners -= it
                        else -> updatesScope.launch(Dispatchers.Main) {
                            @OptIn(NonSymmetricalApi::class)
                            listener.onSharedPreferenceChanged(
                                /*sharedPreferences =*/ this@DataStorePreferences,
                                /*key =*/ key
                            )
                        }
                    }
                }
            }
        }
    }
}