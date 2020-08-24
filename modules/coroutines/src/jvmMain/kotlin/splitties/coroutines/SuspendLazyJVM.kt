/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:JvmMultifileClass
@file:JvmName("SuspendLazyKt")

package splitties.coroutines

import kotlinx.coroutines.Dispatchers
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
fun <T> suspendBlockingLazyIO(
    initializer: () -> T
): SuspendLazy<T> = suspendBlockingLazy(Dispatchers.IO, initializer)
