/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.drawables

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape

typealias NewShapeRef = () -> Shape

inline fun shapeDrawable(
    shape: Shape,
    block: ShapeDrawable.() -> Unit
) = ShapeDrawable(shape).apply(block)

inline fun shapeDrawable(
    createShape: NewShapeRef,
    block: ShapeDrawable.() -> Unit
) = ShapeDrawable(createShape()).apply(block)
