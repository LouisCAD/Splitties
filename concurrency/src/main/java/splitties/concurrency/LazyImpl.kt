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

@file:Suppress("NOTHING_TO_INLINE")

package splitties.concurrency

internal inline fun <T> _lazy(mode: LazyThreadSafetyPolicy, noinline initializer: () -> T): Lazy<T> {
    return if (ConcurrencyReporter.unchecked) kotlin.lazy(LazyThreadSafetyMode.NONE, initializer).also {
        ConcurrencyReporter.atLeastOneUncheckedLazyInstantiated = true
    } else when (mode) {
        LazyThreadSafetyPolicy.UI_THREAD -> UiThreadOnlyLazyImpl(initializer)
        LazyThreadSafetyPolicy.UNIQUE_ACCESS_THREAD -> SingleThreadLazyImpl(initializer)
        LazyThreadSafetyPolicy.UNIQUE_THREAD -> SingleThreadLazyImpl(initializer, true)
    }
}

/**
 * This is a modified version of [kotlin.UnsafeLazyImpl] which checks for UI thread on each access.
 * @param checkInstantiation if true, the thread which will access this [Lazy] should be the same
 *                           that instantiates it. Otherwise, any thread can instantiate it, but
 *                           only the first accessing it will be allowed to access it after.
 */
internal class SingleThreadLazyImpl<out T>(initializer: () -> T, checkInstantiation: Boolean = false)
    : Lazy<T> {
    @Volatile private var threadId = -1L
    private var initializer: (() -> T)? = initializer
    private var _value: Any? = UNINITIALIZED_VALUE

    init {
        if (checkInstantiation) threadId = Thread.currentThread().id
    }

    override val value: T
        get() {
            if (threadId == -1L) synchronized(this) {
                ConcurrencyReporter.check(threadId == -1L) {
                    "Thread $threadId first accessed this single thread lazy, but the thread " +
                            "${Thread.currentThread().id} is accessing it."
                }
                threadId = Thread.currentThread().id
            } else ConcurrencyReporter.check(Thread.currentThread().id == threadId) {
                "Thread $threadId first accessed this single thread lazy, but the thread " +
                        "${Thread.currentThread().id} is accessing it."
            }
            if (_value === UNINITIALIZED_VALUE) {
                _value = initializer!!()
                initializer = null
            }
            @Suppress("UNCHECKED_CAST")
            return _value as T
        }

    override fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE
    override fun toString(): String = if (isInitialized()) value.toString() else NOT_INITIALIZED
}

/**
 * This is a modified version of [kotlin.UnsafeLazyImpl] which checks for UI thread on each access.
 */
internal class UiThreadOnlyLazyImpl<out T>(initializer: () -> T) : Lazy<T> {
    private var initializer: (() -> T)? = initializer
    private var _value: Any? = UNINITIALIZED_VALUE

    override val value: T
        get() {
            ConcurrencyReporter.check(isUiThread) { "This should only be called on the UI thread!" }
            if (_value === UNINITIALIZED_VALUE) {
                _value = initializer!!()
                initializer = null
            }
            @Suppress("UNCHECKED_CAST")
            return _value as T
        }

    override fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE
    override fun toString(): String = if (isInitialized()) value.toString() else NOT_INITIALIZED
}