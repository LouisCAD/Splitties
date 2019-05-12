/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@FlowPreview
suspend fun <E> Flow<E>.collectEachAndCancelPrevious(
    context: CoroutineContext = EmptyCoroutineContext,
    skipEquals: Boolean = false,
    action: suspend CoroutineScope.(E) -> Unit
): Unit = coroutineScope {
    var job: Job? = null
    var previousValue: E? = null // Null is safe as first value because at first, job is null.
    collect { newValue ->
        job?.let { // No skipEquals on first value since there's no job yet.
            if (skipEquals && previousValue == newValue) return@collect else it.cancelAndJoin()
        }
        previousValue = newValue
        job = launch(context) {
            action(newValue)
        }
    }
}
