/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package splitties.lifecycle.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import splitties.coroutines.offerCatching
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.time.*

@ExperimentalTime
@ExperimentalSplittiesApi
actual fun Lifecycle.isResumedFlow(timeout: Duration): Flow<Boolean> = flow {
    isResumedFlow().collectIndexed { index, isResumed ->
        if (isResumed.not() && index != 0) delay(timeout)
        emit(isResumed)
    }
}.distinctUntilChanged()

@ExperimentalTime
@ExperimentalSplittiesApi
actual fun Lifecycle.isStartedFlow(timeout: Duration): Flow<Boolean> = flow {
    isStartedFlow().collectIndexed { index, isStarted ->
        if (isStarted.not() && index != 0) delay(timeout)
        emit(isStarted)
    }
}.distinctUntilChanged()

@ExperimentalSplittiesApi
actual fun Lifecycle.isResumedFlow(): Flow<Boolean> = stateFlow().map {
    it.isAtLeast(Lifecycle.State.RESUMED)
}.distinctUntilChanged()

@ExperimentalSplittiesApi
actual fun Lifecycle.isStartedFlow(): Flow<Boolean> = stateFlow().map {
    it.isAtLeast(Lifecycle.State.STARTED)
}.distinctUntilChanged()

@ExperimentalSplittiesApi
@Suppress("RemoveExplicitTypeArguments") // Remove when new inference is successfully enabled.
actual fun Lifecycle.stateFlow(): Flow<Lifecycle.State> = callbackFlow<Lifecycle.State> {
    val observer = LifecycleEventObserver { _, _ ->
        offerCatching(currentState)
        if (currentState == Lifecycle.State.DESTROYED) channel.close()
    }
    addObserver(observer)
    awaitClose {
        removeObserver(observer)
    }
}.flowOn(Dispatchers.Main.immediate)
