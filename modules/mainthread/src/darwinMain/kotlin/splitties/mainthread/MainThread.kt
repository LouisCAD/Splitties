/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainthread

import platform.Foundation.NSThread

actual val isMainThread: Boolean inline get() = NSThread.isMainThread

@PublishedApi
internal actual val currentThread: Any? get() = NSThread.currentThread
