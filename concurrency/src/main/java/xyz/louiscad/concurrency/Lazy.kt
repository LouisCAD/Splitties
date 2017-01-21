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

package xyz.louiscad.concurrency

import android.os.Looper.getMainLooper
import java.io.Serializable

/**
 * A lazy that checks it's called on the UI thread.
 */
fun <T> uiLazy(initializer: () -> T): Lazy<T> = UiThreadOnlyLazyImpl(initializer)

private object UNINITIALIZED_VALUE

/**
 * This code is a modified version of the kotlin stdlib impl.
 * @see lazy
 */
internal class UiThreadOnlyLazyImpl<out T>(initializer: () -> T) : Lazy<T>, Serializable {
    private var initializer: (() -> T)? = initializer
    private var _value: Any? = UNINITIALIZED_VALUE

    override val value: T
        get() {
            if (check) checkUiThread()
            if (_value === UNINITIALIZED_VALUE) {
                _value = initializer!!()
                initializer = null
            }
            @Suppress("UNCHECKED_CAST")
            return _value as T
        }

    override fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE

    override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."

    private fun writeReplace(): Any = InitializedLazyImpl(value)
}

@Suppress("NOTHING_TO_INLINE")
private inline fun checkUiThread() = check(getMainLooper().thread == Thread.currentThread()) {
    "This should only be called on the UI thread!"
}

private class InitializedLazyImpl<out T>(override val value: T) : Lazy<T>, Serializable {
    override fun isInitialized(): Boolean = true
    override fun toString(): String = value.toString()

}