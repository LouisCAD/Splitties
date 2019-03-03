/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.bundle

import android.os.Bundle
import splitties.mainthread.isMainThread

open class BundleSpec {

    @PublishedApi
    internal var currentBundle: Bundle?
        get() = if (isMainThread) currentBundleMainThread else currentBundleByThread.get()
        set(value) {
            if (isMainThread) currentBundleMainThread = value else currentBundleByThread.set(value)
        }

    @PublishedApi
    internal var isReadOnly: Boolean
        get() = if (isMainThread) isReadOnlyMainThread else isReadOnlyByThread.get() ?: false
        set(value) {
            if (isMainThread) isReadOnlyMainThread = value else isReadOnlyByThread.set(value)
        }

    private var currentBundleMainThread: Bundle? = null
    private val currentBundleByThread by lazy { ThreadLocal<Bundle?>() }
    private var isReadOnlyMainThread = false
    private val isReadOnlyByThread by lazy { ThreadLocal<Boolean>() }
}
