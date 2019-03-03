/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.inflate
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun Context.recyclerView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RecyclerView.() -> Unit = {}
): RecyclerView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return inflate(R.layout.recyclerview_with_scrollbars, id, theme, initView)
}

inline fun View.recyclerView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RecyclerView.() -> Unit = {}
): RecyclerView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.recyclerView(id, theme, initView)
}

inline fun Ui.recyclerView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RecyclerView.() -> Unit = {}
): RecyclerView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.recyclerView(id, theme, initView)
}

@ExperimentalSplittiesApi
inline fun RecyclerView.LayoutManager.verticalListLayoutParams(
    block: RecyclerView.LayoutParams.() -> Unit = {}
): RecyclerView.LayoutParams {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return generateDefaultLayoutParams().apply {
        width = MATCH_PARENT
        height = WRAP_CONTENT
    }.apply(block)
}

@ExperimentalSplittiesApi
inline fun RecyclerView.LayoutManager.horizontalListLayoutParams(
    block: RecyclerView.LayoutParams.() -> Unit = {}
): RecyclerView.LayoutParams {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return generateDefaultLayoutParams().apply {
        width = WRAP_CONTENT
        height = MATCH_PARENT
    }.apply(block)
}
