/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * `false` by default.
 * If you can guarantee that all the items that this [RecyclerView] will display
 * (as defined from its adapter), have all the same size (e.g. homogeneous height for a list
 * displayed with a vertical [LinearLayoutManager]), set this property to `true` so the
 * [RecyclerView] can enable some optimizations that will improve efficiency.
 *
 * @see RecyclerView.setHasFixedSize
 */
inline var RecyclerView.fixedSize: Boolean
    get() = hasFixedSize()
    set(value) = setHasFixedSize(value)
