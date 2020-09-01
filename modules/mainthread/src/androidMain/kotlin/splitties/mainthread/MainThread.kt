/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainthread

import android.os.Looper

/** This main looper cache avoids synchronization overhead when accessed repeatedly. */
@JvmField
val mainLooper: Looper = Looper.getMainLooper()!!
@JvmField
val mainThread: Thread = mainLooper.thread

actual val isMainThread: Boolean inline get() = mainThread === Thread.currentThread()

@PublishedApi
internal actual val currentThread: Any? inline get() = Thread.currentThread()
