/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

import kotlinx.coroutines.channels.SendChannel

fun <E> SendChannel<E>.offerCatching(element: E): Boolean {
    return runCatching { offer(element) }.getOrDefault(false)
}
