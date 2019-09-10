/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

expect fun runTest(block: suspend CoroutineScope.() -> Unit)

@ExperimentalTime
fun runTest(timeout: Duration, block: suspend CoroutineScope.() -> Unit) = runTest {
    withTimeout(timeout.toLongMilliseconds(), block)
}
