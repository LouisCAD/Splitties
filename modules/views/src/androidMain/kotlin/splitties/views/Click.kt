/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views

import android.view.View

/**
 * Registers the [block] lambda as [View.OnClickListener] to this View.
 *
 * If this View is not clickable, it becomes clickable.
 */
inline fun View.onClick(block: View.OnClickListener) = setOnClickListener(block)

/**
 * Register the [block] lambda as [View.OnLongClickListener] to this View.
 * By default, [consume] is set to true because it's the most common use case, but you can set it
 * to false.
 * If you want to return a value dynamically, use [View.setOnLongClickListener] instead.
 *
 * If this view is not long clickable, it becomes long clickable.
 */
inline fun View.onLongClick(
    consume: Boolean = true,
    crossinline block: () -> Unit
) = setOnLongClickListener { block(); consume }
