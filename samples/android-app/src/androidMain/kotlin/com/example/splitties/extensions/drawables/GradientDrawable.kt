/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.extensions.drawables

import android.graphics.drawable.GradientDrawable
import com.example.splitties.extensions.NO_GETTER
import com.example.splitties.extensions.noGetter

inline fun gradientDrawable(
    shape: Int = GradientDrawable.RECTANGLE,
    block: GradientDrawable.() -> Unit = {}
) = GradientDrawable().apply {
    this.shape = shape
    block()
}

inline fun rectangleDrawable(block: GradientDrawable.() -> Unit = {}) = GradientDrawable().apply(block)
inline fun ovalDrawable(block: GradientDrawable.() -> Unit = {}) = gradientDrawable(GradientDrawable.OVAL, block)
inline fun lineDrawable(block: GradientDrawable.() -> Unit = {}) = gradientDrawable(GradientDrawable.LINE, block)
inline fun ringDrawable(block: GradientDrawable.() -> Unit = {}) = gradientDrawable(GradientDrawable.RING, block)


inline var GradientDrawable.solidColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) = setColor(value)
