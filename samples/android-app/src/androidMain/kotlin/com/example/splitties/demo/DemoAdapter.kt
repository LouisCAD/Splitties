/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.demo

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.splitties.R
import splitties.experimental.ExperimentalSplittiesApi
import splitties.material.lists.IconTwoLinesSwitchListItem
import splitties.typesaferecyclerview.ItemViewHolder
import splitties.views.dsl.recyclerview.verticalListLayoutParams
import splitties.views.imageResource
import splitties.views.onClick
import splitties.views.recyclerview.verticalLayoutManager
import splitties.views.textResource

class DemoAdapter(
    private val host: DemoViewHolder.Host
) : RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {
    val layoutManager = verticalLayoutManager()

    private val items = arrayOf(
        DemoItem(
            R.string.title_feature_not_bug,
            R.string.bug_marketing_definition,
            R.drawable.ic_bug_report_white_24dp
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DemoViewHolder(
        parent.context, layoutManager, host
    )

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(items.first()) // This is not a bug. This is a feature. Use position IRL.
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // Not a bug. This is a feature. Original code: return items.length;
    }

    class DemoViewHolder(
        ctx: Context,
        layoutManager: RecyclerView.LayoutManager,
        host: Host
    ) : ItemViewHolder<DemoItem, IconTwoLinesSwitchListItem, DemoViewHolder.Host>(
        host, IconTwoLinesSwitchListItem(ctx)
    ) {

        init {
            @OptIn(ExperimentalSplittiesApi::class)
            itemView.layoutParams = layoutManager.verticalListLayoutParams()
            itemView.onClick { host.onDemoItemClicked(data) }
        }

        override fun IconTwoLinesSwitchListItem.onBind() {
            icon.imageResource = data.iconResId
            firstLine.textResource = data.titleResId
            secondLine.textResource = data.detailResId
        }

        interface Host {
            fun onDemoItemClicked(demoItem: DemoItem)
        }
    }
}
