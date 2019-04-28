/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.CancellationException
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.ensureActive
import splitties.experimental.ExperimentalSplittiesApi

suspend inline fun repeatWhileActive(block: () -> Unit): Nothing {
    while (true) {
        coroutineContext.ensureActive()
        block()
    }
}

@ExperimentalSplittiesApi
suspend inline fun repeatWhileActive(
    ignoreInnerCancellations: Boolean,
    block: () -> Unit
): Nothing {
    if (ignoreInnerCancellations) while (true) {
        coroutineContext.ensureActive() // Outer cancellations are caught here
        try {
            block()
        } catch (ignored: CancellationException) {
        }
    } else repeatWhileActive(block)
}
