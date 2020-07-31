/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import splitties.experimental.ExperimentalSplittiesApi
import splitties.experimental.InternalSplittiesApi

/**
 * **WARNING:** This API is not thread-safe, so don't use it if you use the target
 * [Preferences] subclass on different threads.
 */
@ExperimentalSplittiesApi
inline fun <P : Preferences> P.edit(
    blocking: Boolean = false,
    crossinline editions: P.() -> Unit
) {
    @OptIn(InternalSplittiesApi::class)
    beginEdit(blocking)
    try {
        editions()
        @OptIn(InternalSplittiesApi::class)
        endEdit()
    } catch (t: Throwable) {
        @OptIn(InternalSplittiesApi::class)
        abortEdit()
        throw t
    }
}
