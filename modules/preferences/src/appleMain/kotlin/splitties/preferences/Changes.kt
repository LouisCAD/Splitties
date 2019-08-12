/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSUserDefaultsDidChangeNotification
import splitties.mainthread.isMainThread

@UseExperimental(ExperimentalCoroutinesApi::class)
internal actual fun SharedPreferences.changesFlow(
    key: String,
    emitAfterRegister: Boolean
): Flow<Unit> = channelFlow<Unit> {
    if (this@changesFlow is NSUserDefaultsBackedSharedPreferences &&
        useNotificationCenterForChanges
    ) {
        val defaultNotificationCenter = NSNotificationCenter.defaultCenter
        val observer = defaultNotificationCenter.addObserverForName(
            name = NSUserDefaultsDidChangeNotification,
            `object` = userDefaults,
            queue = if (isMainThread) NSOperationQueue.mainQueue else null
        ) {
            runCatching { offer(Unit) }
        }
        if (emitAfterRegister) runCatching { offer(Unit) }
        awaitClose {
            defaultNotificationCenter.removeObserver(observer)
        }
    } else {
        val listener = OnSharedPreferenceChangeListener { _, changedKey ->
            if (key == changedKey) runCatching { offer(Unit) }
        }
        registerOnSharedPreferenceChangeListener(listener)
        if (emitAfterRegister) runCatching { offer(Unit) }
        awaitClose {
            unregisterOnSharedPreferenceChangeListener(listener)
        }

    }
}.conflate()
