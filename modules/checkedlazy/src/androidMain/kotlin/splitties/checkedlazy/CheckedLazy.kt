/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.checkedlazy

/**
 * Returns a lazy that throws an [IllegalStateException] if its value is accessed outside of main thread.
 */
fun <T> mainThreadLazy(
    initializer: () -> T
): Lazy<T> = CheckedAccessLazyImpl(initializer, mainThreadChecker)

/**
 * Creates a new instance of the [Lazy] that uses the specified initialization
 * function [initializer] and calls [readChecker] on each access.
 *
 * If you want to check access to always happen on a specific thread, you can pass the result of
 * a call to the [accessOn] helper function. There's also the [noAccessOn] helper function that does
 * the contrary.
 *
 * Let's say you want to throw an [IllegalStateException] for a custom condition, it's [readChecker]
 * responsibility to do it. [check] or [require] from stdlib may help you implement this kind
 * of check.
 *
 * This lazy is as safe as the [readChecker] is: an empty one will be like calling [kotlin.lazy] with
 * [kotlin.LazyThreadSafetyMode.NONE], which is potentially unsafe.
 *
 * @param readChecker This method may check any condition external to this [Lazy].
 */
fun <T> checkedLazy(readChecker: () -> Unit, initializer: () -> T): Lazy<T> {
    return CheckedAccessLazyImpl(initializer, readChecker)
}

/**
 * This is a modified version of [kotlin.UnsafeLazyImpl] which calls [readCheck] on each access.
 * If you also supplied [firstAccessCheck] (using secondary constructor), it will be called on the
 * first access, then [readCheck] will be called for subsequent accesses.
 */
internal class CheckedAccessLazyImpl<out T>(
    initializer: () -> T,
    private val readCheck: (() -> Unit)? = null,
    private var firstAccessCheck: (() -> Unit)? = null
) : Lazy<T> {

    private var initializer: (() -> T)? = initializer
    private var _value: Any? = uninitializedValue

    override val value: T
        get() {
            if (_value === uninitializedValue) {
                firstAccessCheck?.invoke() ?: readCheck?.invoke()
                _value = initializer!!()
                firstAccessCheck = null
                initializer = null
            } else readCheck?.invoke()
            @Suppress("UNCHECKED_CAST")
            return _value as T
        }

    override fun isInitialized(): Boolean = _value !== uninitializedValue
    override fun toString(): String = if (isInitialized()) value.toString() else NOT_INITIALIZED
}

internal val uninitializedValue = Any()
internal const val NOT_INITIALIZED = "Lazy value not initialized yet."
