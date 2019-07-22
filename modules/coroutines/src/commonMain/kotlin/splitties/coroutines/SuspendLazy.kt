/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:JvmMultifileClass
@file:JvmName("SuspendLazyKt")

package splitties.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

fun <T> suspendBlockingLazy(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: () -> T
): SuspendLazy<T> = SuspendLazyBlockingImpl(dispatcher, initializer)

fun <T> suspendLazy(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: suspend () -> T
): SuspendLazy<T> = SuspendLazySuspendingImpl(dispatcher, initializer)

interface SuspendLazy<out T> {
    suspend operator fun invoke(): T
}

private class SuspendLazyBlockingImpl<out T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: () -> T
) : SuspendLazy<T> {
    private val lazyValue = lazy(initializer)
    override suspend operator fun invoke(): T = with(lazyValue) {
        if (isInitialized()) value else withContext(dispatcher) { value }
    }
}

private class SuspendLazySuspendingImpl<out T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: suspend () -> T
) : SuspendLazy<T> {
    private val lazyValue = lazy { GlobalScope.async(dispatcher) { initializer() } }
    override suspend operator fun invoke(): T = with(lazyValue) {
        if (isInitialized()) value else withContext(dispatcher) { value }
    }.await()
}
