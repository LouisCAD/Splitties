/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.core

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.FrameLayout
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Default gravity is treated by FrameLayout as: [Gravity.TOP] or [Gravity.START].
 */
inline fun FrameLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    @SuppressLint("InlinedApi")
    gravity: Int = FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY,
    initParams: FrameLayout.LayoutParams.() -> Unit = {}
): FrameLayout.LayoutParams {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return FrameLayout.LayoutParams(width, height).also {
        it.gravity = gravity
    }.apply(initParams)
}
