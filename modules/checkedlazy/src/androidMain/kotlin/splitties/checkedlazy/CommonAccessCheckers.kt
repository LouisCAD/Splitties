/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.checkedlazy

import splitties.mainthread.checkMainThread

@JvmField
internal val mainThreadChecker = { checkMainThread() }

/**
 * Throws an [IllegalStateException] if invoked outside of passed [thread].
 *
 * Helper for [checkedLazy]'s readChecker.
 */
fun accessOn(thread: Thread): () -> Unit = {
    Thread.currentThread().let {
        if (thread !== it) error("Access expected on thread: $thread. Current: $it")
    }
}

/**
 * Throws an [IllegalStateException] if invoked from passed [thread].
 *
 * Helper for [checkedLazy]'s readChecker.
 */
fun noAccessOn(thread: Thread): () -> Unit = {
    Thread.currentThread().let {
        if (thread === it) error("No access allowed on thread: $thread!")
    }
}
