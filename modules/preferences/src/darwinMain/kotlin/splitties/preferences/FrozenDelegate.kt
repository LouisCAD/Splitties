/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlin.concurrent.AtomicReference
import kotlin.native.concurrent.freeze
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class FrozenDelegate<T>(initialValue: T) : ReadWriteProperty<Any?, T> {

    private val reference = AtomicReference(initialValue.freeze())

    init {
        freeze()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return reference.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        reference.value = value.freeze()
    }
}
