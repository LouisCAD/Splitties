/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.SendChannel
import splitties.experimental.ExperimentalSplittiesApi

/**
 * [SendChannel.offer] that returns `false` when this [SendChannel.isClosedForSend], instead of
 * throwing.
 *
 * [SendChannel.offer] throws when the channel is closed. In race conditions, especially when using
 * multithreaded dispatchers, that can lead to uncaught exceptions as offer is often called from
 * non suspending functions that don't catch the default [CancellationException] or any other
 * exception that might be the cause of the closing of the channel.
 *
 * See this issue: [https://github.com/Kotlin/kotlinx.coroutines/issues/974](https://github.com/Kotlin/kotlinx.coroutines/issues/974)
 */
@ExperimentalSplittiesApi
fun <E> SendChannel<E>.offerCatching(element: E): Boolean {
    return runCatching { offer(element) }.getOrDefault(false)
}
