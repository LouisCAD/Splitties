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
 *
 * NOTE: Will be deprecated then removed once [this PR](https://github.com/Kotlin/kotlinx.coroutines/pull/2225)
 * makes it into a kotlinx.coroutines release.
 */
@FutureOverlapWithKotlinXCoroutines
suspend inline fun awaitCancellation(): Nothing = suspendCancellableCoroutine {}
