/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import splitties.experimental.ExperimentalSplittiesApi
import splitties.experimental.InternalSplittiesApi
import splitties.views.recyclerview.horizontalLayoutManager
import splitties.views.recyclerview.verticalLayoutManager

@InternalSplittiesApi
class SingleViewAdapter<V : View>(
    private val view: V,
    vertical: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val layoutManager = if (vertical) verticalLayoutManager() else horizontalLayoutManager()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = view.apply {
        @OptIn(ExperimentalSplittiesApi::class)
        layoutParams = layoutManager.verticalListLayoutParams()
    }.let { object : RecyclerView.ViewHolder(it) {} }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit
    override fun getItemCount() = 1
}
