/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.views

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.os.Build.VERSION_CODES.M
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.View
import android.widget.TextView
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
    set(@StyleRes value) = if (SDK_INT < M) setTextAppearance(context, value)
    else setTextAppearance(value)

fun TextView.centerText() {
    if (SDK_INT >= JELLY_BEAN_MR1) textAlignment = View.TEXT_ALIGNMENT_CENTER
    gravity = Gravity.CENTER
}

fun TextView.alignTextToStart() {
    if (SDK_INT >= JELLY_BEAN_MR1) textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    gravity = Gravity.START
}

fun TextView.alignTextToEnd() {
    if (SDK_INT >= JELLY_BEAN_MR1) textAlignment = View.TEXT_ALIGNMENT_VIEW_END
    gravity = Gravity.END
}

fun TextView.setCompoundDrawables(start: Drawable? = null, top: Drawable? = null,
                                  end: Drawable? = null, bottom: Drawable? = null,
                                  intrinsicBounds: Boolean = false) {
    val left = if (isLtr) start else end
    val right = if (isRtl) start else end
    if (intrinsicBounds) setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    else setCompoundDrawables(left, top, right, bottom)
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.setCompoundDrawables(@DrawableRes start: Int = 0, @DrawableRes top: Int = 0,
                                         @DrawableRes end: Int = 0, @DrawableRes bottom: Int = 0) {
    val left = if (isLtr) start else end
    val right = if (isRtl) start else end
    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.clearCompoundDrawables() = setCompoundDrawables(null, null, null, null)
