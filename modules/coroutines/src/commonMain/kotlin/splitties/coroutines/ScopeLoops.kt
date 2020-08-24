/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ensureActive
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.coroutines.coroutineContext

/**
 * As of Kotlin 1.3, `while (true)` evaluates to [Unit] instead of [Nothing] in lambdas, and using
 * `coroutineContext.ensureActive()` would add another line of boilerplate, so this inline extension
 * function can be handy. The fact that is is inline allows you to do a non local return just like
 * you would from a while loop.
 */
@ExperimentalSplittiesApi
suspend inline fun repeatWhileActive(block: () -> Unit): Nothing {
    while (true) {
        coroutineContext.ensureActive()
        block()
    }
}

/**
 * As of Kotlin 1.3, `while (true)` evaluates to [Unit] instead of [Nothing] in lambdas, and using
 * `coroutineContext.ensureActive()` would add another line of boilerplate, so this inline extension
 * function can be handy. The fact that is is inline allows you to do a non local return just like
 * you would from a while loop.
 *
 * If [ignoreInnerCancellations] is `true`, [CancellationException]s thrown from the [block] will be
 * caught and ignored. Next iteration will still check for cancellation, so it will exit safely by
 * throwing it if the entire scope is cancelled. This gives a chance to recover from local
 * cancellations in an iteration.
 */
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
