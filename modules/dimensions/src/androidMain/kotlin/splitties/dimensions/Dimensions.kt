/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.dimensions

import android.content.Context
import android.view.View

inline fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
inline fun Context.dip(value: Float): Float = (value * resources.displayMetrics.density)
inline fun View.dip(value: Int): Int = context.dip(value)
inline fun View.dip(value: Float): Float = context.dip(value)

inline fun Context.dp(value: Int): Int = dip(value)
inline fun Context.dp(value: Float): Float = dip(value)
inline fun View.dp(value: Int): Int = context.dip(value)
inline fun View.dp(value: Float): Float = context.dip(value)
