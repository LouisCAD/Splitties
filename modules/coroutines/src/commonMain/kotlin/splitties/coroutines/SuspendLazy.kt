/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:JvmMultifileClass
@file:JvmName("SuspendLazyKt")

package splitties.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

fun <T> suspendBlockingLazy(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    initializer: () -> T
): SuspendLazy<T> = SuspendLazyBlockingImpl(dispatcher, initializer)

fun <T> CoroutineScope.suspendLazy(
    context: CoroutineContext = EmptyCoroutineContext,
    initializer: suspend () -> T
): SuspendLazy<T> = SuspendLazySuspendingImpl(this, context, initializer)

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
    private val context: CoroutineContext,
    initializer: suspend () -> T
) : SuspendLazy<T> {
    private val value by lazy { coroutineScope.async(context) { initializer() } }
    override suspend operator fun invoke(): T = value.await()
}
