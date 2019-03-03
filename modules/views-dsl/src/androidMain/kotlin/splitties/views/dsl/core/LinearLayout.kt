/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.core

import android.widget.LinearLayout
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun LinearLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    initParams: LinearLayout.LayoutParams.() -> Unit = {}
): LinearLayout.LayoutParams {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return LinearLayout.LayoutParams(width, height).apply(initParams)
}

inline fun LinearLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    gravity: Int = -1,
    weight: Float = 0f,
    initParams: LinearLayout.LayoutParams.() -> Unit = {}
): LinearLayout.LayoutParams {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return LinearLayout.LayoutParams(width, height).also {
        it.gravity = gravity
        it.weight = weight
    }.apply(initParams)
}
