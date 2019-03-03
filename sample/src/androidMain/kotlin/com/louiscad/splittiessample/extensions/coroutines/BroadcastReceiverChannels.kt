/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample.extensions.coroutines

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import splitties.init.appCtx

@Suppress("NOTHING_TO_INLINE")
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
inline fun CoroutineScope.conflatedBroadcastReceiverChannel(
    action: String,
    priority: Int = 0
): ReceiveChannel<Intent> = broadcastReceiverChannel(action, priority, capacity = Channel.CONFLATED)

@Suppress("NOTHING_TO_INLINE")
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
inline fun CoroutineScope.broadcastReceiverChannel(
    action: String,
    priority: Int = 0,
    capacity: Int = Channel.UNLIMITED
): ReceiveChannel<Intent> = broadcastReceiverChannel(
    IntentFilter(action).also { it.priority = priority },
    capacity
)

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun CoroutineScope.broadcastReceiverChannel(
    filter: IntentFilter,
    capacity: Int = Channel.UNLIMITED
): ReceiveChannel<Intent> = produce {
    val receiveChannel = Channel<Intent>(capacity = capacity)
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            receiveChannel.offer(intent)
        }
    }
    val ctx = appCtx
    ctx.registerReceiver(receiver, filter)
    try {
        receiveChannel.consumeEach {
            send(it)
        }
    } finally {
        ctx.unregisterReceiver(receiver)
        receiveChannel.close()
    }
}
