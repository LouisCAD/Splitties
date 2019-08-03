/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainthread

actual val isMainThread: Boolean inline get() = true // Only one thread in JS.

@PublishedApi
internal actual val currentThread: Any? get() = null // No Thread object in JS.
