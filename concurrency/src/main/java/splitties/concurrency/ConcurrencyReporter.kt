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

import splitties.concurrency.Concurrency.MODE_REPORT
import splitties.concurrency.Concurrency.mode
import splitties.concurrency.Concurrency.unchecked
import timber.log.Timber

@Suppress("NOTHING_TO_INLINE")
object ConcurrencyReporter {

    inline internal fun check(value: Boolean, lazyMessage: () -> Any) {
        if (!unchecked) try {
            kotlin.check(value, lazyMessage)
        } catch (e: IllegalStateException) {
            if (mode == MODE_REPORT) Timber.wtf(e) else throw e
        }
    }
}