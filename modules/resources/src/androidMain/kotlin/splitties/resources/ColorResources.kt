/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(InternalSplittiesApi::class)

package splitties.resources

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build.VERSION.SDK_INT
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import splitties.exceptions.illegalArg
import splitties.experimental.InternalSplittiesApi
import splitties.init.appCtx

/**
 * @see [androidx.core.content.ContextCompat.getColor]
 */
@ColorInt
fun Context.color(@ColorRes colorRes: Int): Int = if (SDK_INT >= 23) getColor(colorRes) else {
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
 * @see [androidx.core.content.ContextCompat.getColorStateList]
 */
fun Context.colorSL(@ColorRes colorRes: Int): ColorStateList {
    return (if (SDK_INT >= 23) getColorStateList(colorRes) else {
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

@ColorInt
fun Context.styledColor(@AttrRes attr: Int): Int = withResolvedThemeAttribute(attr) {
    when  {
        type in TypedValue.TYPE_FIRST_COLOR_INT..TypedValue.TYPE_LAST_COLOR_INT -> data
        type == TypedValue.TYPE_STRING && string.startsWith("res/color/") -> color(resourceId)
        else -> illegalArg(unexpectedThemeAttributeTypeErrorMessage(expectedKind = "color"))
    }
}

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

fun Context.styledColorSL(@AttrRes attr: Int): ColorStateList = withResolvedThemeAttribute(attr) {
    if (type in TypedValue.TYPE_FIRST_COLOR_INT..TypedValue.TYPE_LAST_COLOR_INT) {
        ColorStateList.valueOf(data)
    } else if (type == TypedValue.TYPE_STRING && string.startsWith("res/color/")) {
        colorSL(resourceId)
    } else {
        illegalArg(errorMessage = unexpectedThemeAttributeTypeErrorMessage(expectedKind = "color"))
    }
}

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
