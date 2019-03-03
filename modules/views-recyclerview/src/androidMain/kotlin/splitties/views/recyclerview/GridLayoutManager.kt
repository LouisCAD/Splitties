/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.recyclerview

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import splitties.resources.int

inline fun gridLayoutManager(
    spanCount: Int,
    reverseLayout: Boolean = false,
    setup: GridLayoutManager.() -> Unit = {}
) = GridLayoutManager(null, spanCount, RecyclerView.VERTICAL, reverseLayout).apply(setup)

inline fun gridLayoutManager(
    context: Context,
    @IntegerRes spanCountRes: Int,
    reverseLayout: Boolean = false,
    setup: GridLayoutManager.() -> Unit = {}
) = gridLayoutManager(context.int(spanCountRes), reverseLayout, setup)

inline fun horizontalGridLayoutManager(
    spanCount: Int,
    reverseLayout: Boolean = false,
    setup: GridLayoutManager.() -> Unit = {}
) = GridLayoutManager(null, spanCount, RecyclerView.HORIZONTAL, reverseLayout).apply(setup)

inline fun horizontalGridLayoutManager(
    context: Context,
    @IntegerRes spanCountRes: Int,
    reverseLayout: Boolean = false,
    setup: GridLayoutManager.() -> Unit = {}
) = horizontalGridLayoutManager(context.int(spanCountRes), reverseLayout, setup)
