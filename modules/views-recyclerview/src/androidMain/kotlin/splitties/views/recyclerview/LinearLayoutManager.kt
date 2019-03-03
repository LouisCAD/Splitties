/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun verticalLayoutManager(
    reverseLayout: Boolean = false,
    setup: LinearLayoutManager.() -> Unit = {}
) = LinearLayoutManager(null, RecyclerView.VERTICAL, reverseLayout).apply(setup)

inline fun horizontalLayoutManager(
    reverseLayout: Boolean = false,
    setup: LinearLayoutManager.() -> Unit = {}
) = LinearLayoutManager(null, RecyclerView.HORIZONTAL, reverseLayout).apply(setup)
