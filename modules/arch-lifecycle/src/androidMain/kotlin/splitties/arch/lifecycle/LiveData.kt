/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

@ObsoleteSplittiesLifecycleApi
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline observer: (t: T?) -> Unit
): Observer<T> = Observer<T> {
    observer(it)
}.also { liveData.observe(this, it) }

@ObsoleteSplittiesLifecycleApi
inline fun <T : Any> LifecycleOwner.observeNotNull(
    liveData: LiveData<T>,
    crossinline observer: (t: T) -> Unit
): Observer<T> = Observer<T> {
    if (it != null) observer(it)
}.also { liveData.observe(this, it) }

@ObsoleteSplittiesLifecycleApi
@JvmName("observeWithLiveDataOfNullable")
inline fun <T : Any> LifecycleOwner.observeNotNull(
    liveData: LiveData<T?>,
    crossinline observer: (t: T) -> Unit
): Observer<T?> = Observer<T?> {
    if (it != null) observer(it)
}.also { liveData.observe(this, it) }

/**
 * Applies the given function on the main thread to each value emitted by source
 * LiveData and returns LiveData, which emits resulting values.
 *
 * The given function [transform] will be **executed on the main thread**.
 *
 * @param transform   a function to apply
 * @param X           a type of source LiveData
 * @param Y           a type of resulting LiveData.
 * @return            a LiveData which emits resulting values
 */
@Deprecated(
    message = "Use the one from androidx now.",
    ReplaceWith("map(transform)", "androidx.lifecycle.map")
)
inline fun <X, Y> LiveData<X>.map(
    crossinline transform: (X?) -> Y
): LiveData<Y> = map { input -> transform(input) }

@Deprecated(
    "Bad semantics. Doesn't follow the conventional behavior of mapNotNull from Kotlin."
)
inline fun <X, Y> LiveData<X>.mapNotNull(
    crossinline transform: (X) -> Y
): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) { x ->
        if (x != null) result.value = transform(x)
    }
    return result
}

@Deprecated(
    message = "Use the one from androidx now.",
    ReplaceWith("switchMap(transform)", "androidx.lifecycle.switchMap")
)
inline fun <X, Y> LiveData<X>.switchMap(
    crossinline transform: (X?) -> LiveData<Y>?
): LiveData<Y> = switchMap { input -> transform(input) }

@Deprecated(
    "Bad semantics. Doesn't follow the conventional behavior of mapNotNull from Kotlin."
)
@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.switchMapNotNull(
    crossinline transform: (X) -> LiveData<Y>?
): LiveData<Y> = switchMap { input: X? ->
    input?.let { transform(it) }
}
