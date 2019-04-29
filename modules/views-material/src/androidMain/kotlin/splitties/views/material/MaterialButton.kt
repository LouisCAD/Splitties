/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.material

import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.google.android.material.button.MaterialButton

inline var MaterialButton.iconResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@DrawableRes value) = setIconResource(value)

inline var MaterialButton.iconTintAndTextColor: ColorStateList
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        iconTint = value
        setTextColor(value)
    }
