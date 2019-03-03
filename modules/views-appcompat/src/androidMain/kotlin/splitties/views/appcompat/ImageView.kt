/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.appcompat

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat

inline var ImageView.imgTintList: ColorStateList?
    get() = ImageViewCompat.getImageTintList(this)
    set(value) = ImageViewCompat.setImageTintList(this, value)

inline var ImageView.imgTintMode: PorterDuff.Mode?
    get() = ImageViewCompat.getImageTintMode(this)
    set(value) = ImageViewCompat.setImageTintMode(this, value)
