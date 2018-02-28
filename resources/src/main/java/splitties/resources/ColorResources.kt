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

package splitties.resources

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.view.View
import splitties.init.appCtx

/**
 * @see [android.support.v4.content.ContextCompat.getColor]
 */
@ColorInt
fun Context.color(@ColorRes colorRes: Int): Int = if (SDK_INT >= M) getColor(colorRes) else {
    @Suppress("DEPRECATION")
    resources.getColor(colorRes)
}

inline fun Fragment.color(@ColorRes colorRes: Int) = context!!.color(colorRes)
inline fun View.color(@ColorRes colorRes: Int) = context.color(colorRes)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appColor(@ColorRes colorRes: Int) = appCtx.color(colorRes)

/**
 * @see [android.support.v4.content.ContextCompat.getColorStateList]
 */
fun Context.colorSL(@ColorRes colorRes: Int): ColorStateList? {
    return (if (SDK_INT >= M) getColorStateList(colorRes) else {
        @Suppress("DEPRECATION")
        resources.getColorStateList(colorRes)
    })
}

inline fun Fragment.colorSL(@ColorRes colorRes: Int) = context!!.colorSL(colorRes)
inline fun View.colorSL(@ColorRes colorRes: Int) = context.colorSL(colorRes)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appColorSL(@ColorRes colorRes: Int) = appCtx.colorSL(colorRes)

// Styled resources below

private inline val defaultColor get() = Color.RED

@ColorInt
fun Context.styledColor(@AttrRes attr: Int): Int = withStyledAttributes(attr) { getColor(it, defaultColor) }

inline fun Fragment.styledColor(@AttrRes attr: Int) = context!!.styledColor(attr)
inline fun View.styledColor(@AttrRes attr: Int) = context.styledColor(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledColor(@AttrRes attr: Int) = appCtx.styledColor(attr)

fun Context.styledColorSL(@AttrRes attr: Int): ColorStateList? = withStyledAttributes(attr) { getColorStateList(it) }
inline fun Fragment.styledColorSL(@AttrRes attr: Int) = context!!.styledColorSL(attr)
inline fun View.styledColorSL(@AttrRes attr: Int) = context.styledColorSL(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledColorSL(@AttrRes attr: Int) = appCtx.styledColorSL(attr)
