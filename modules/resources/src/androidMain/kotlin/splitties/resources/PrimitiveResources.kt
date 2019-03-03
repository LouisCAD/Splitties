/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.resources

import android.content.Context
import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import splitties.init.appCtx

inline fun Context.bool(@BoolRes boolResId: Int): Boolean = resources.getBoolean(boolResId)
inline fun Fragment.bool(@BoolRes boolResId: Int) = context!!.bool(boolResId)
inline fun View.bool(@BoolRes boolResId: Int) = context.bool(boolResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appBool(@BoolRes boolResId: Int) = appCtx.bool(boolResId)

inline fun Context.int(@IntegerRes intResId: Int): Int = resources.getInteger(intResId)
inline fun Fragment.int(@IntegerRes intResId: Int) = context!!.int(intResId)
inline fun View.int(@IntegerRes intResId: Int) = context.int(intResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appInt(@IntegerRes intResId: Int) = appCtx.int(intResId)

inline fun Context.intArray(
    @ArrayRes intArrayResId: Int
): IntArray = resources.getIntArray(intArrayResId)

inline fun Fragment.intArray(@ArrayRes intArrayResId: Int) = context!!.intArray(intArrayResId)
inline fun View.intArray(@ArrayRes intArrayResId: Int) = context.intArray(intArrayResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appIntArray(@ArrayRes intArrayResId: Int) = appCtx.intArray(intArrayResId)

// Styled resources below

fun Context.styledBool(
    @AttrRes attr: Int
): Boolean = withStyledAttributes(attr) { getBoolean(it, false) }

inline fun Fragment.styledBool(@AttrRes attr: Int) = context!!.styledBool(attr)
inline fun View.styledBool(@AttrRes attr: Int) = context.styledBool(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledBool(@AttrRes attr: Int) = appCtx.styledBool(attr)

fun Context.styledInt(@AttrRes attr: Int): Int = withStyledAttributes(attr) { getInteger(it, -1) }
inline fun Fragment.styledInt(@AttrRes attr: Int) = context!!.styledInt(attr)
inline fun View.styledInt(@AttrRes attr: Int) = context.styledInt(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledInt(@AttrRes attr: Int) = appCtx.styledInt(attr)
