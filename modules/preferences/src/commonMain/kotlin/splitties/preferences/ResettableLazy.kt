/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlin.reflect.KProperty

/**
 * A Lazy delegate that keeps a reference to its initializer and resets its value if you set its
 * value with its current value.
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
internal expect class ResettableLazy<T : Any>constructor(initializer: () -> T) {

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T)

    fun invalidate()
}
