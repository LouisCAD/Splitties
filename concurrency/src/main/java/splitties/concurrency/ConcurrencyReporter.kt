/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package splitties.concurrency

import android.app.Application
import android.content.ContentProvider
import splitties.init.appCtx
import timber.log.Timber

@Suppress("NOTHING_TO_INLINE")
object ConcurrencyReporter {

    const val MODE_CHECK = 0
    const val MODE_REPORT = 1
    const val MODE_UNCHECKED = 2

    internal var atLeastOneUncheckedLazyInstantiated = false

    /**
     * The default mode is [MODE_CHECK]. You can override the integer resource
     * [R.integer.splitties_concurrency_mode] and set it to [MODE_REPORT] or [MODE_UNCHECKED]
     * to change the default behavior.
     *
     * If you modify this property at runtime (i.e. dynamically, independently from the integer
     * resource mentioned above), you shouldn't set this field from [MODE_UNCHECKED] to
     * another value after lazy have been instantiated because [MODE_UNCHECKED] falls back to
     * the kotlin's default implementation, and this implementation is not affect by changes of
     * this property, which mean the already instantiated lazy instances during [MODE_UNCHECKED]
     * can't switch to [MODE_REPORT] or [MODE_CHECK].
     * If your production app needs to be able to switch between the different available modes, it's
     * recommended to set the desired mode from a [ContentProvider] or if you have a custom
     * [Application] class, in the [Application.onCreate] method.
     */
    var mode: Int = appCtx.resources.getInteger(R.integer.splitties_concurrency_mode)
        set(value) {
            if (field == value) return
            if (field == MODE_UNCHECKED && atLeastOneUncheckedLazyInstantiated) {
                val e = IllegalStateException("At least one unchecked lazy instantiated before!")
                when (value) {
                    MODE_REPORT -> Timber.wtf(e)
                    MODE_CHECK -> throw e
                }
            }
            field = value
        }

    inline val unchecked get() = mode == MODE_UNCHECKED

    inline internal fun check(value: Boolean, lazyMessage: () -> Any) {
        if (!unchecked) try {
            kotlin.check(value, lazyMessage)
        } catch (e: IllegalStateException) {
            if (mode == MODE_REPORT) Timber.wtf(e) else throw e
        }
    }
}