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
package splitties.uithread

import splitties.mainthread.checkMainThread
import splitties.mainthread.isMainThread
import splitties.mainthread.mainThread

private const val UI_THREAD_NAMING_DEPRECATION_MESSAGE = "Although the UI thread is the " +
        "main thread by default, this is not always the case. Prefer referring to the main " +
        "thread instead."

@Deprecated(UI_THREAD_NAMING_DEPRECATION_MESSAGE,
        ReplaceWith("mainThread", "splitties.mainthread.mainThread")
)
inline val uiThread
    get() = mainThread

@Deprecated(UI_THREAD_NAMING_DEPRECATION_MESSAGE,
        ReplaceWith("isMainThread", "splitties.mainthread.isMainThread")
)
val isUiThread
    inline get() = isMainThread

@Deprecated(UI_THREAD_NAMING_DEPRECATION_MESSAGE,
        ReplaceWith("checkMainThread()", "splitties.mainthread.checkMainThread")
)
@Suppress("NOTHING_TO_INLINE")
inline fun checkUiThread() = checkMainThread()
