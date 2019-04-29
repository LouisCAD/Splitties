/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.receiver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import splitties.init.appCtx

private typealias Receiver = BroadcastReceiver.(intent: Intent) -> Unit

class BroadcastReceiver(private val filter: IntentFilter, private val receiver: Receiver) {

    constructor(
        vararg actions: String,
        priority: Int = 0,
        receiver: Receiver
    ) : this(filter = IntentFilter().also {
        check(actions.isNotEmpty()) { "Need actions for the IntentFilter!" }
        it.priority = priority
        for (action in actions) it.addAction(action)
    }, receiver = receiver)

    fun register() {
        appCtx.registerReceiver(broadcastReceiver, filter)
    }

    fun unregister() = appCtx.unregisterReceiver(broadcastReceiver)

    private val broadcastReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(ctx: Context, intent: Intent) = receiver(intent)
    }
}
