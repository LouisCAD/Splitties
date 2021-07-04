/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalSplittiesApi
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.whileStarted(lifecycle: Lifecycle): Flow<T> {
    return lifecycle.isStartedFlow().flatMapLatest { isStarted ->
        if (isStarted) this else emptyFlow()
    }
}

@ExperimentalTime
@ExperimentalSplittiesApi
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.whileStarted(lifecycle: Lifecycle, timeout: Duration): Flow<T> {
    return lifecycle.isStartedFlow(timeout = timeout).flatMapLatest { isStarted ->
        if (isStarted) this else emptyFlow()
    }
}
