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

package splitties.checkedlazy

import splitties.exceptions.illegal
import splitties.uithread.checkUiThread

@JvmField internal val uiChecker = { checkUiThread() }

/**
 * Throws an [IllegalStateException] if invoked outside of passed [thread].
 *
 * Helper for [checkedLazy]'s readChecker.
 */
fun accessOn(thread: Thread) = {
    Thread.currentThread().let {
        if (thread !== it) illegal("Access expected on thread: $thread. Current: $it")
    }
}

/**
 * Throws an [IllegalStateException] if invoked from passed [thread].
 *
 * Helper for [checkedLazy]'s readChecker.
 */
fun noAccessOn(thread: Thread) = {
    Thread.currentThread().let {
        if (thread === it) illegal("No access allowed on thread: $thread!")
    }
}
