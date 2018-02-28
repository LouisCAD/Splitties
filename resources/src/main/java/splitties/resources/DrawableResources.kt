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
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.annotation.AttrRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.View
import splitties.init.appCtx

private val tmpValue by lazy { TypedValue() }

/**
 * @see [android.support.v4.content.ContextCompat.getDrawable]
 */
fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? {
    @Suppress("CascadeIf")
    return if (SDK_INT >= LOLLIPOP) getDrawable(drawableResId)
    else if (SDK_INT >= 16) {
        @Suppress("DEPRECATION")
        resources.getDrawable(drawableResId)
    } else {
        // Prior to JELLY_BEAN, Resources.getDrawable() would not correctly
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

fun Context.styledDrawable(@AttrRes attr: Int): Drawable? = withStyledAttributes(attr) { getDrawable(it) }
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
