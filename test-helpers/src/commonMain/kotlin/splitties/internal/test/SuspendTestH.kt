/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

expect fun runTest(
    alsoRunInNativeWorker: Boolean = false,
    skipIfRoboelectric: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
)

@ExperimentalTime
fun runTest(
    timeout: Duration,
    alsoRunInNativeWorker: Boolean = false,
    skipIfRoboelectric: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) = runTest(
    alsoRunInNativeWorker = alsoRunInNativeWorker,
    skipIfRoboelectric = skipIfRoboelectric
) {
    withTimeout(timeout.inWholeMilliseconds, block)
}
