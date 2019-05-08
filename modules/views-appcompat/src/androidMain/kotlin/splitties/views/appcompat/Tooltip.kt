/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.appcompat

import android.os.Build.VERSION.SDK_INT
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import kotlin.DeprecationLevel.HIDDEN

inline fun View.contentDescAsTooltip() {
    tooltipTxt = contentDescription
}

var View.tooltipTxt: CharSequence?
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = if (SDK_INT >= 26) tooltipText = value else {
        TooltipCompat.setTooltipText(this, value)
    }
