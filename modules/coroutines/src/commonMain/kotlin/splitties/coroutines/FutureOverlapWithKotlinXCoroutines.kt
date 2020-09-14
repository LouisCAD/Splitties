/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

@RequiresOptIn(
    message = "These declarations will overlap with future APIs from kotlinx.coroutines. " +
        "Once the kotlinx.coroutines API is there, they will be deprecated with warning," +
        "then with error level, before being removed in a subsequent release of Splitties."
)
annotation class FutureOverlapWithKotlinXCoroutines
