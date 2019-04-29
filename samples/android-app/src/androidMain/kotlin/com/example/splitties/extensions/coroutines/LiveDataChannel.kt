/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import splitties.arch.lifecycle.LifecycleObserver

@ExperimentalCoroutinesApi
fun <T> LifecycleOwner.channelOf(
    liveData: LiveData<T>
): ReceiveChannel<T?> = Channel<T?>(Channel.CONFLATED).also {
    val observer = Observer<T?> { t -> it.offer(t) }
    liveData.observe(this, observer)
    it.invokeOnClose { liveData.removeObserver(observer) }
    lifecycle.addObserver(object : LifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            it.close()
        }
    })
}
