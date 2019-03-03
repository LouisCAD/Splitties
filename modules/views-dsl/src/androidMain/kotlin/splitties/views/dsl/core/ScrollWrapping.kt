/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.core

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.annotation.IdRes
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun View.wrapInScrollView(
    @IdRes id: Int = View.NO_ID,
    height: Int = wrapContent,
    initView: ScrollView.() -> Unit = {}
): ScrollView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::ScrollView, id) {
        add(this@wrapInScrollView, lParams(width = matchParent, height = height))
    }.apply(initView)
}

inline fun View.wrapInHorizontalScrollView(
    @IdRes id: Int = View.NO_ID,
    height: Int = wrapContent,
    initView: HorizontalScrollView.() -> Unit = {}
): HorizontalScrollView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::HorizontalScrollView, id) {
        add(this@wrapInHorizontalScrollView, lParams(width = matchParent, height = height))
    }.apply(initView)
}
