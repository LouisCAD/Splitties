/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import splitties.experimental.NonSymmetricalApi

@OptIn(ExperimentalCoroutinesApi::class)
internal actual fun SharedPreferences.changesFlow(
    key: String,
    emitAfterRegister: Boolean
): Flow<Unit> = channelFlow<Unit> {
    @OptIn(NonSymmetricalApi::class)
    val listener = OnSharedPreferenceChangeListener { _, changedKey ->
        if (key == changedKey) runCatching { offer(Unit) }
    }
    registerOnSharedPreferenceChangeListener(listener)
    if (emitAfterRegister) runCatching { offer(Unit) }
    awaitClose {
        unregisterOnSharedPreferenceChangeListener(listener)
    }
}.conflate()
