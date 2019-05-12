/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

@ObsoleteCoroutinesApi
suspend inline fun <E> ReceiveChannel<E>.doOnEach(
    context: CoroutineContext = EmptyCoroutineContext,
    noinline action: suspend (E) -> Unit
) = CoroutineScope(coroutineContext).launch(context) { consumeEach { action(it) } }

suspend fun <E> ReceiveChannel<E>.consumeEachAndCancelPrevious(
    context: CoroutineContext = EmptyCoroutineContext,
    skipEquals: Boolean = false,
    action: suspend CoroutineScope.(E) -> Unit
): Unit = coroutineScope {
    var job: Job? = null
    var previousValue: E? = null // Null is safe as first value because at first, job is null.
    @UseExperimental(ObsoleteCoroutinesApi::class)
    consumeEach { newValue ->
        job?.let { // No skipEquals on first value since there's no job yet.
            if (skipEquals && previousValue == newValue) return@consumeEach else it.cancelAndJoin()
        }
        previousValue = newValue
        job = launch(context) {
            action(newValue)
        }
    }
}
