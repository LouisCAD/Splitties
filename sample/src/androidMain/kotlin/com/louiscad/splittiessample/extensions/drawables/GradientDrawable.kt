/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.louiscad.splittiessample.extensions.drawables

import android.graphics.drawable.GradientDrawable
import com.louiscad.splittiessample.extensions.NO_GETTER
import com.louiscad.splittiessample.extensions.noGetter

inline var GradientDrawable.solidColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) = setColor(value)
