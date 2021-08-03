/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.resources

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import splitties.init.appCtx

private val tmpValue by lazy { TypedValue() }

/**
 * @see [androidx.core.content.ContextCompat.getDrawable]
 */
fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? {
    @Suppress("CascadeIf")
    return if (SDK_INT >= 21) getDrawable(drawableResId)
    else if (SDK_INT >= 16) {
        @Suppress("DEPRECATION")
        resources.getDrawable(drawableResId)
    } else {
        // Prior to API 16, Resources.getDrawable() would not correctly
        // retrieve the final configuration density when the resource ID
        // is a reference another Drawable resource. As a workaround, try
        // to resolve the drawable reference manually.
        val resolvedId = synchronized(tmpValue) {
            resources.getValue(drawableResId, tmpValue, true)
            tmpValue.resourceId
        }
        @Suppress("DEPRECATION")
        resources.getDrawable(resolvedId)
    }
}

inline fun Fragment.drawable(@DrawableRes drawableResId: Int) = context!!.drawable(drawableResId)
inline fun View.drawable(@DrawableRes drawableResId: Int) = context.drawable(drawableResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appDrawable(@DrawableRes drawableResId: Int) = appCtx.drawable(drawableResId)


// Styled resources below

fun Context.styledDrawable(@AttrRes attr: Int): Drawable? = drawable(resolveThemeAttribute(attr))

inline fun Fragment.styledDrawable(@AttrRes attr: Int) = context!!.styledDrawable(attr)
inline fun View.styledDrawable(@AttrRes attr: Int) = context.styledDrawable(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledDrawable(@AttrRes attr: Int) = appCtx.styledDrawable(attr)
