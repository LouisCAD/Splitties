/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Suspends until cancellation, in which case it will throw a [CancellationException].
 *
 * Handy because it returns [Nothing], allowing it to be used in any coroutine,
 * regardless of the required return type.
 */
suspend inline fun awaitCancellation(): Nothing = suspendCancellableCoroutine {}
