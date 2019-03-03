/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.annotation.LayoutRes
import splitties.systemservices.layoutInflater

/**
 * @see LayoutInflater.inflate
 */
@CheckResult
inline fun <V : View> LayoutInflater.inflate(@LayoutRes layoutResId: Int): V {
    @Suppress("UNCHECKED_CAST") return inflate(layoutResId, null, false) as V
}

/**
 * @see LayoutInflater.inflate
 */
@CheckResult
inline fun <V : View> Context.inflate(@LayoutRes layoutResId: Int): V {
    return layoutInflater.inflate(layoutResId)
}

/**
 * @param attachToRoot must be explicitly false if used for a `RecyclerView` item.
 *
 * @see inflateAndAttach
 * @see LayoutInflater.inflate
 */
inline fun <V : View> ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean): V {
    @Suppress("UNCHECKED_CAST") return layoutInflater.inflate(layoutRes, this, attachToRoot) as V
}

/**
 * @see LayoutInflater.inflate
 */
inline fun ViewGroup.inflateAndAttach(@LayoutRes layoutRes: Int) {
    layoutInflater.inflate(layoutRes, this, true)
}
