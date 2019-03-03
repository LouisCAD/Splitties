/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun View.wrapInRecyclerView(
    horizontal: Boolean = false,
    @IdRes id: Int = View.NO_ID,
    initView: RecyclerView.() -> Unit = {}
): RecyclerView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return wrapInRecyclerView(horizontal, id).apply(initView)
}

fun View.wrapInRecyclerView(
    horizontal: Boolean = false,
    @IdRes id: Int = View.NO_ID
): RecyclerView = recyclerView(id) {
    val contentAdapter = SingleViewAdapter(this@wrapInRecyclerView, vertical = !horizontal)
    layoutManager = contentAdapter.layoutManager
    adapter = contentAdapter
}
