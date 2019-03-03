/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlin.reflect.KProperty

/**
 * A Lazy delegate that keeps a reference to it's initializer and resets it's value if you set it's
 * value with it's current value.
 *
 * Note that this doesn't support nullable values.
 *
 * Example:
 * ```kotlin
 * var myMember by ResettableLazy { Editor(preferences) }
 * ...
 * myMember = myMember // This line resets myMember. Next access will call the initializer again.
 * ```
 */
internal class ResettableLazy<T : Any>(private val initializer: () -> T) {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        return value ?: initializer().apply { value = this }
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {
        check(this.value == value) { "New values aren't accepted to reset this delegated property" }
        invalidate()
    }

    fun invalidate() {
        value = null
    }
}
