/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import splitties.init.appCtx

@Suppress("NOTHING_TO_INLINE")
inline fun conflatedBroadcastReceiverChannel(
    action: String,
    priority: Int = 0
): ReceiveChannel<Intent> = broadcastReceiverChannel(
    action = action,
    priority = priority,
    capacity = Channel.CONFLATED
)

@Suppress("NOTHING_TO_INLINE")
inline fun broadcastReceiverChannel(
    action: String,
    priority: Int = 0,
    capacity: Int = Channel.UNLIMITED
): ReceiveChannel<Intent> = broadcastReceiverChannel(
    filter = IntentFilter(action).also { it.priority = priority },
    capacity = capacity
)

fun broadcastReceiverChannel(
    filter: IntentFilter,
    capacity: Int = Channel.UNLIMITED
): ReceiveChannel<Intent> {
    val channel = Channel<Intent>(capacity = capacity)
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            channel.offerCatching(intent)
        }
    }
    val ctx = appCtx
    @UseExperimental(ExperimentalCoroutinesApi::class)
    channel.invokeOnClose {
        ctx.unregisterReceiver(receiver)
    }
    ctx.registerReceiver(receiver, filter)
    return channel
}
