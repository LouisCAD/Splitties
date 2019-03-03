/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.resources

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.AttrRes
import splitties.mainthread.isMainThread

private val uiThreadConfinedCachedAttrArray = IntArray(1)
private val cachedAttrArray = IntArray(1)

inline fun <T> Context.withStyledAttributes(
    @AttrRes attrRes: Int,
    func: TypedArray.(firstIndex: Int) -> T
): T = obtainStyledAttr(attrRes).let { styledAttrs ->
    styledAttrs.func(styledAttrs.getIndex(0)).also { styledAttrs.recycle() }
}

@PublishedApi
@SuppressLint("Recycle") // Recycled in function above.
internal fun Context.obtainStyledAttr(@AttrRes attrRes: Int): TypedArray = if (isMainThread) {
    uiThreadConfinedCachedAttrArray[0] = attrRes
    obtainStyledAttributes(uiThreadConfinedCachedAttrArray)
} else synchronized(cachedAttrArray) {
    cachedAttrArray[0] = attrRes
    obtainStyledAttributes(cachedAttrArray)
}
