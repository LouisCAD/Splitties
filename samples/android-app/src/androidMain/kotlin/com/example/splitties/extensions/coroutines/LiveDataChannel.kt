/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import splitties.arch.lifecycle.LifecycleObserver
import splitties.coroutines.offerCatching
import splitties.lifecycle.coroutines.MainAndroid
import splitties.mainthread.checkMainThread

@ExperimentalCoroutinesApi
fun <T> LifecycleOwner.channelOf(
    liveData: LiveData<T>
): ReceiveChannel<T?> {
    checkMainThread()
    return Channel<T?>(Channel.CONFLATED).also {
        val observer = Observer<T?> { t -> it.offerCatching(t) }
        liveData.observe(this, observer)
        it.invokeOnClose { liveData.removeObserver(observer) }
        lifecycle.addObserver(object : LifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                it.close()
            }
        })
    }
}

@UseExperimental(ExperimentalCoroutinesApi::class)
fun <T> LifecycleOwner.flowOf(liveData: LiveData<T>): Flow<T?> = flow {
    @UseExperimental(FlowPreview::class)
    emitAll(channelOf(liveData).consumeAsFlow())
}.flowOn(Dispatchers.MainAndroid)
