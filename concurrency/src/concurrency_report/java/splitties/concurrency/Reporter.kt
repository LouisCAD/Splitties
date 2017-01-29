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

import timber.log.Timber

@Suppress("NOTHING_TO_INLINE")
internal object Reporter {
    inline fun check(value: Boolean) = try {
        kotlin.check(value)
    } catch (e: IllegalStateException) {
        Timber.wtf(e)
    }

    inline fun check(value: Boolean, lazyMessage: () -> Any) = try {
        kotlin.check(value, lazyMessage)
    } catch (e: IllegalStateException) {
        Timber.wtf(e)
    }
}