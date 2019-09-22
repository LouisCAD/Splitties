/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.recyclerview

import androidx.recyclerview.widget.RecyclerView

/**
 * `false` by default.
 * If you can know in advance that this [RecyclerView] size will not be affected
 * by the adapter contents, set this property to `true` so the [RecyclerView]
 * can enable some optimizations that will improve efficiency.
 *
 * @see RecyclerView.setHasFixedSize
 */
inline var RecyclerView.fixedSize: Boolean
    get() = hasFixedSize()
    set(value) = setHasFixedSize(value)
