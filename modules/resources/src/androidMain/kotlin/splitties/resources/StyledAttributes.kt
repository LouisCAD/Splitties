/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.resources

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import splitties.mainthread.isMainThread

@AnyRes
fun Context.resolveThemeAttribute(
    @AttrRes attrRes: Int,
    resolveRefs: Boolean = true
): Int = if (isMainThread) {
    if (theme.resolveAttribute(attrRes, uiThreadConfinedCachedTypeValue, resolveRefs).not()) {
        throw Resources.NotFoundException(
            "Couldn't resolve attribute resource #0x" + Integer.toHexString(attrRes)
                    + " from the theme of this Context."
        )
    }
    uiThreadConfinedCachedTypeValue.resourceId
} else synchronized(cachedTypeValue) {
    if (theme.resolveAttribute(attrRes, cachedTypeValue, resolveRefs).not()) {
        throw Resources.NotFoundException(
            "Couldn't resolve attribute resource #0x" + Integer.toHexString(attrRes)
                    + " from the theme of this Context."
        )
    }
    cachedTypeValue.resourceId
}

private val uiThreadConfinedCachedTypeValue = TypedValue()
private val cachedTypeValue = TypedValue()


@Deprecated("IDE Preview doesn't support this way of resolving a theme attribute. " +
        "Use the resolveThemeAttribute function to directly get the resource id instead.")
inline fun <T> Context.withStyledAttributes(
    @AttrRes attrRes: Int,
    func: TypedArray.(firstIndex: Int) -> T
): T = obtainStyledAttr(attrRes).let { styledAttrs ->
    styledAttrs.func(styledAttrs.getIndex(0)).also { styledAttrs.recycle() }
}

//TODO: Remove when withStyledAttributes is removed from API.
@PublishedApi
@SuppressLint("Recycle") // Recycled in function above.
internal fun Context.obtainStyledAttr(@AttrRes attrRes: Int): TypedArray = if (isMainThread) {
    uiThreadConfinedCachedAttrArray[0] = attrRes
    obtainStyledAttributes(uiThreadConfinedCachedAttrArray)
} else synchronized(cachedAttrArray) {
    cachedAttrArray[0] = attrRes
    obtainStyledAttributes(cachedAttrArray)
}

private val uiThreadConfinedCachedAttrArray = IntArray(1)
private val cachedAttrArray = IntArray(1)
