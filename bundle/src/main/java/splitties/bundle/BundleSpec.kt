/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
