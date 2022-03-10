/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.drawables

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape

fun Shape.toDrawable(block: ShapeDrawable.() -> Unit): ShapeDrawable {
    return ShapeDrawable(this).apply(block)
}
