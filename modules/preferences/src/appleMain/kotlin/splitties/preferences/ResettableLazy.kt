/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlin.reflect.KProperty

internal actual class ResettableLazy<T : Any> actual constructor(private val initializer: () -> T) {

    private var value: T? by FrozenDelegate(null)

    actual operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        return value ?: initializer().apply { value = this }
    }

    actual operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {
        check(this.value == value) { "New values aren't accepted to reset this delegated property" }
        invalidate()
    }

    actual fun invalidate() {
        value = null
    }
}
