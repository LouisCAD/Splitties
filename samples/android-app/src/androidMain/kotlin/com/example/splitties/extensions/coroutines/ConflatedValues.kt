/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * This class is designed to combine (conflate) values from multiple callbacks or channels into
 * a single channel.
 */
abstract class ConflatedValues<CV : ConflatedValues<CV, T>, T> private constructor(
    private val updateChannel: Channel<T>,
    private val conflateAll: CV.() -> T
) : ReceiveChannel<T> by updateChannel {
    constructor(conflateAll: CV.() -> T) : this(Channel(CONFLATED), conflateAll)

    /** Can be called from a subclass method to perform an arbitrary update/refresh. */
    protected fun triggerUpdate() {
        @Suppress("UNCHECKED_CAST")
        updateChannel.offer((this as CV).conflateAll())
    }

    protected fun <E> conflatedValue(initialValue: E): ReadWriteProperty<Any?, E> =
        Value(initialValue)

    private inner class Value<E>(private var value: E) : ReadWriteProperty<Any?, E> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: E) {
            this.value = value
            triggerUpdate()
        }
    }
}

/**
 * This class is designed to combine (conflate) value changes from multiple callbacks or channels
 * into a single channel. If you need to have actual values into the channel, use [ConflatedValues].
 */
abstract class ConflatedUpdates<CV : ConflatedUpdates<CV>> : ConflatedValues<CV, Unit>({})
