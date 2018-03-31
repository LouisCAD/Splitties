/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.louiscad.splittiessample.demo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.louiscad.splittiessample.R
import splitties.material.lists.IconTwoLinesSwitchListItem
import splitties.typesaferecyclerview.ItemViewHolder
import splitties.viewdsl.recyclerview.verticalListLayoutParams
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
