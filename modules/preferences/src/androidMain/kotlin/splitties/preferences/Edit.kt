/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
inline fun <P : Preferences> P.edit(blocking: Boolean = false, editions: P.() -> Unit) {
    beginEdit(blocking)
    try {
        editions()
        endEdit()
    } catch (t: Throwable) {
        abortEdit()
        throw t
    }
}
