package com.louiscad.splittiessample.extensions.coroutines

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
