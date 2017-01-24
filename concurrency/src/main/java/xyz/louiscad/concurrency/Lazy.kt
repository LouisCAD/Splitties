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

package xyz.louiscad.concurrency

import android.os.Looper.getMainLooper

/**
 * A lazy that checks it's called on the UI thread.
 */
inline fun <T> uiLazy(noinline initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyPolicy.UI_THREAD, initializer)
}

/**
 * Creates a new instance of the [Lazy] that is already initialized with the specified [value].
 */
inline fun <T> lazyOf(value: T) = kotlin.lazyOf(value)

/**
 * Creates a new instance of the [Lazy] that uses the specified initialization function [initializer]
 * and the default thread-safety mode [LazyThreadSafetyMode.SYNCHRONIZED].
 *
 * If the initialization of a value throws an exception, it will attempt to reinitialize the value at next access.
 *
 * Note that the returned instance uses itself to synchronize on. Do not synchronize from external code on
 * the returned instance as it may cause accidental deadlock. Also this behavior can be changed in the future.
 */
inline fun <T> lazy(noinline initializer: () -> T) = kotlin.lazy(initializer)

/**
 * Creates a new instance of the [Lazy] that uses the specified initialization function [initializer]
 * and thread-safety [mode].
 *
 * If the initialization of a value throws an exception, it will attempt to reinitialize the value at next access.
 *
 * Note that when the [LazyThreadSafetyMode.SYNCHRONIZED] mode is specified the returned instance uses itself
 * to synchronize on. Do not synchronize from external code on the returned instance as it may cause accidental deadlock.
 * Also this behavior can be changed in the future.
 */
inline fun <T> lazy(mode: LazyThreadSafetyMode, noinline initializer: () -> T) = kotlin.lazy(mode, initializer)

fun <T> lazy(mode: LazyThreadSafetyPolicy, initializer: () -> T): Lazy<T> = _lazy(mode, initializer)

/**
 * Creates a new instance of the [Lazy] that uses the specified initialization function [initializer]
 * and the default thread-safety mode [LazyThreadSafetyMode.SYNCHRONIZED].
 *
 * If the initialization of a value throws an exception, it will attempt to reinitialize the value at next access.
 *
 * The returned instance uses the specified [lock] object to synchronize on.
 * When the [lock] is not specified the instance uses itself to synchronize on,
 * in this case do not synchronize from external code on the returned instance as it may cause accidental deadlock.
 * Also this behavior can be changed in the future.
 */
inline fun <T> lazy(lock: Any?, noinline initializer: () -> T) = kotlin.lazy(lock, initializer)

/**
 * Specifies how a [Lazy] instance synchronizes access among multiple threads.
 */
enum class LazyThreadSafetyPolicy {
    /**
     * The [Lazy] instance should only be accessed on the UI thread.
     *
     * Depending on the flavor of this
     * library, it will throw an exception (default flavor), log an error (report flavor) with
     * [Timber](https://github.com/JakeWharton/timber) which can be redirected to your crash
     * reporting solution, or don't even check the thread with the unchecked flavor, for maximum
     * performance in release, if a thread policy violation is very unlikely, or not reporting
     * worthy.
     */
    UI_THREAD,
    /**
     * The [Lazy] instance can be created on any thread, but should only be accessed on the thread
     * that first accessed the value.
     *
     * Which thread accesses the [Lazy] is checked with [Thread.getId].
     */
    UNIQUE_ACCESS_THREAD,
    /**
     * The [Lazy] instance should only be accessed on the thread that created it.
     *
     * **This is different from [UNIQUE_ACCESS_THREAD].**
     *
     * Which thread accesses the [Lazy] is checked with [Thread.getId].
     */
    UNIQUE_THREAD
}

internal object UNINITIALIZED_VALUE
internal val NOT_INITIALIZED = "Lazy value not initialized yet."

internal inline fun isUiThread() = getMainLooper().thread == Thread.currentThread()
