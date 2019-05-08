/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.mainthread

import android.os.Looper

/** This main looper cache avoids synchronization overhead when accessed repeatedly. */
@JvmField
val mainLooper: Looper = Looper.getMainLooper()!!
@JvmField
val mainThread: Thread = mainLooper.thread

val isMainThread inline get() = mainThread === Thread.currentThread()

/**
 * Passes if run on the [mainThread] (aka. UI thread), throws an [IllegalStateException] otherwise.
 */
inline fun checkMainThread() = check(isMainThread) {
    "This should ONLY be called on the main thread! Current: ${Thread.currentThread()}"
}

/**
 * Passes if not run on the [mainThread] (aka. UI thread), throws an [IllegalStateException]
 * otherwise.
 */
inline fun checkNotMainThread() = check(!isMainThread) {
    "This should NEVER be called on the main thread! Current: ${Thread.currentThread()}"
}
