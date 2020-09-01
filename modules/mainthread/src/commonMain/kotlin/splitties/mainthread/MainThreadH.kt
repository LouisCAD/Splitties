/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.mainthread

expect val isMainThread: Boolean

/**
 * Passes if run on the main thread (aka. UI thread), throws an [IllegalStateException] otherwise.
 */
inline fun checkMainThread() = check(isMainThread) {
    "This should ONLY be called on the main thread! Current: $currentThread"
}

/**
 * Passes if not run on the main thread (aka. UI thread), throws an [IllegalStateException]
 * otherwise.
 */
inline fun checkNotMainThread() = check(!isMainThread) {
    "This should NEVER be called on the main thread! Current: $currentThread"
}

@PublishedApi
internal expect val currentThread: Any?
