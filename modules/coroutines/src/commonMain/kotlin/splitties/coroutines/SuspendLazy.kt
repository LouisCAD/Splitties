/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:JvmMultifileClass
@file:JvmName("SuspendLazyKt")

package splitties.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

@ExperimentalSplittiesApi
fun <T> suspendBlockingLazy(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: () -> T
): SuspendLazy<T> = SuspendLazyBlockingImpl(dispatcher, initializer)

@ExperimentalSplittiesApi
fun <T> CoroutineScope.suspendLazy(
    context: CoroutineContext = EmptyCoroutineContext,
    initializer: suspend CoroutineScope.() -> T
): SuspendLazy<T> = SuspendLazySuspendingImpl(this, context, initializer)

@ExperimentalSplittiesApi
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
    coroutineScope: CoroutineScope,
    context: CoroutineContext,
    initializer: suspend CoroutineScope.() -> T
) : SuspendLazy<T> {
    private val deferred by lazy { coroutineScope.async(context, start = LAZY, block = initializer) }
    override suspend operator fun invoke(): T = deferred.await()
}
