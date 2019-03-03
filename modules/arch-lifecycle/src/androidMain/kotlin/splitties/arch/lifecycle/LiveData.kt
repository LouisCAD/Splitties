/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

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
@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.map(
    crossinline transform: (X?) -> Y
): LiveData<Y> = Transformations.map(this) { input -> transform(input) }

@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.mapNotNull(
    crossinline transform: (X) -> Y
): LiveData<Y> = Transformations.map(this) { input: X? ->
    input?.let { transform(it) }
}

@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.switchMap(
    crossinline transform: (X?) -> LiveData<Y>?
): LiveData<Y> = Transformations.switchMap(this) { input -> transform(input) }

@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.switchMapNotNull(
    crossinline transform: (X) -> LiveData<Y>?
): LiveData<Y> = Transformations.switchMap(this) { input: X? ->
    input?.let { transform(it) }
}
