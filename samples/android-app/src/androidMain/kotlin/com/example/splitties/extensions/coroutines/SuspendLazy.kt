/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class SuspendLazy<out T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: () -> T
) {
    private val lazyValue = lazy(initializer)
    suspend operator fun invoke(): T = with(lazyValue) {
        if (isInitialized()) value else withContext(dispatcher) { value }
    }
}
