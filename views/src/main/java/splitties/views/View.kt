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

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.support.annotation.ColorInt
import android.view.View

inline fun View.onClick(noinline l: (v: View?) -> Unit) = setOnClickListener { l(it) }

/**
 * True if this View's visibility is [View.VISIBLE].
 *
 * If you set it to false, then this View's visibility will become [View.INVISIBLE].
 * See [showIf] and [gone] if you want to have [View.GONE] instead.
 */
inline var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

/**
 * True if this View's visibility is [View.GONE].
 *
 * If you set it to false, then this View's visibility will become [View.VISIBLE].
 *
 * @see showIf
 * @see visible
 */
inline var View.gone: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }

/**
 * Sets this View's visibility to [View.VISIBLE] if [condition] is true, [View.GONE] otherwise.
 *
 * @see visible
 * @see gone
 */
inline fun View.showIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

/**
 * This View's background [Drawable].
 *
 * This is an alias to the `background` property that is backwards compatible below API 16.
 */
inline var View.bg: Drawable?
    get() = background
    @Suppress("DEPRECATION")
    set(value) = if (SDK_INT < 16) setBackgroundDrawable(value) else background = value

/**
 * Set only property that takes a color int.
 *
 * This is an alias to [View.setBackgroundColor].
 */
inline var View.backgroundColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@ColorInt colorInt) = setBackgroundColor(colorInt)

/**
 * True if layout direction is **left to right**, like in English.
 *
 * This is always true on API 16 and below since layout direction support only came in API 17.
 *
 * @see isRtl
 */
inline val View.isLtr get() = SDK_INT < 17 || layoutDirection == View.LAYOUT_DIRECTION_LTR

/**
 * True if layout direction is **right to left**, like in Arabic.
 *
 * This is always false on API 16 and below since layout direction support only came in API 17.
 *
 * @see isLtr
 */
inline val View.isRtl get() = !isLtr
