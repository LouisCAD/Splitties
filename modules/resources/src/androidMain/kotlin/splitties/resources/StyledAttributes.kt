/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.resources

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import splitties.experimental.InternalSplittiesApi
import splitties.mainthread.isMainThread

@AnyRes
fun Context.resolveThemeAttribute(
    @AttrRes attrRes: Int,
    resolveRefs: Boolean = true
): Int = if (isMainThread) {
    if (theme.resolveAttribute(attrRes, uiThreadConfinedCachedTypedValue, resolveRefs).not()) {
        throw Resources.NotFoundException(
            "Couldn't resolve attribute resource #0x" + Integer.toHexString(attrRes)
                    + " from the theme of this Context."
        )
    }
    uiThreadConfinedCachedTypedValue.resourceId
} else synchronized(cachedTypeValue) {
    if (theme.resolveAttribute(attrRes, cachedTypeValue, resolveRefs).not()) {
        throw Resources.NotFoundException(
            "Couldn't resolve attribute resource #0x" + Integer.toHexString(attrRes)
                    + " from the theme of this Context."
        )
    }
    cachedTypeValue.resourceId
}

@InternalSplittiesApi
inline fun <R> Context.withResolvedThemeAttribute(
    @AttrRes attrRes: Int,
    resolveRefs: Boolean = true,
    crossinline block: TypedValue.() -> R
): R = if (isMainThread) {
    if (theme.resolveAttribute(attrRes, uiThreadConfinedCachedTypedValue, resolveRefs).not()) {
        throw Resources.NotFoundException(
            "Couldn't resolve attribute resource #0x" + Integer.toHexString(attrRes)
                    + " from the theme of this Context."
        )
    }
    block(uiThreadConfinedCachedTypedValue)
} else synchronized(cachedTypeValue) {
    if (theme.resolveAttribute(attrRes, cachedTypeValue, resolveRefs).not()) {
        throw Resources.NotFoundException(
            "Couldn't resolve attribute resource #0x" + Integer.toHexString(attrRes)
                    + " from the theme of this Context."
        )
    }
    block(cachedTypeValue)
}

@PublishedApi @JvmField internal val uiThreadConfinedCachedTypedValue = TypedValue()
@PublishedApi @JvmField internal val cachedTypeValue = TypedValue()

internal fun TypedValue.unexpectedThemeAttributeTypeErrorMessage(expectedKind: String): String {
    val article = when (expectedKind.firstOrNull() ?: ' ') {
        in "aeio" -> "an"
        else -> "a"
    }
    return "Expected $article $expectedKind theme attribute but got type 0x${type.toString(16)} " +
            "(see what it corresponds to in android.util.TypedValue constants)"
}
