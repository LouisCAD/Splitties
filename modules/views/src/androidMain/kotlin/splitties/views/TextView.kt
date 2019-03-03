/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import splitties.resources.color
import kotlin.DeprecationLevel.HIDDEN

inline var TextView.textResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@StringRes value) = setText(value)

inline var TextView.textColorResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@ColorRes value) = setTextColor(color(value))

var TextView.textAppearance: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    @Suppress("DEPRECATION")
    set(@StyleRes value) = if (SDK_INT < 23) setTextAppearance(context, value)
    else setTextAppearance(value)

inline var TextView.lines: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setLines(value)

fun TextView.centerText() {
    if (SDK_INT >= 17) textAlignment = View.TEXT_ALIGNMENT_CENTER
    gravity = Gravity.CENTER
}

fun TextView.alignTextToStart() {
    if (SDK_INT >= 17) textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    gravity = Gravity.START
}

fun TextView.alignTextToEnd() {
    if (SDK_INT >= 17) textAlignment = View.TEXT_ALIGNMENT_VIEW_END
    gravity = Gravity.END
}

fun TextView.setCompoundDrawables(
    start: Drawable? = null, top: Drawable? = null,
    end: Drawable? = null, bottom: Drawable? = null,
    intrinsicBounds: Boolean = false
) {
    val left = if (isLtr) start else end
    val right = if (isRtl) start else end
    if (intrinsicBounds) setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    else setCompoundDrawables(left, top, right, bottom)
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.setCompoundDrawables(
    @DrawableRes start: Int = 0, @DrawableRes top: Int = 0,
    @DrawableRes end: Int = 0, @DrawableRes bottom: Int = 0
) {
    val left = if (isLtr) start else end
    val right = if (isRtl) start else end
    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.clearCompoundDrawables() = setCompoundDrawables(null, null, null, null)
