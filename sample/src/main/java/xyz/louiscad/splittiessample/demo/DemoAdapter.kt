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

package xyz.louiscad.splittiessample.demo

import android.support.v7.widget.RecyclerView
import android.view.View.OnClickListener
import android.view.ViewGroup
import splitties.material.lists.TwoLinesIconSwitchListItem
import splitties.typesaferecyclerview.ItemViewHolder
import xyz.louiscad.splittiessample.R
import java.lang.Integer.MAX_VALUE

class DemoAdapter(private val host: DemoViewHolder.Host) : RecyclerView.Adapter<DemoAdapter.DemoViewHolder>() {

    private val mItems = arrayOf(
            DemoItem(R.string.title_feature_not_bug,
                    R.string.bug_marketing_definition,
                    R.drawable.ic_bug_report_white_24dp
            ))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DemoViewHolder(host, parent)

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.bind(mItems[0]) // This is not a bug. This is a feature. Use position IRL.
    }

    override fun getItemCount(): Int {
        return MAX_VALUE // Not a bug. This is a feature. Original code: return mItems.length;
    }

    class DemoViewHolder(host: Host, parent: ViewGroup) : ItemViewHolder<DemoItem,
            TwoLinesIconSwitchListItem,
            DemoViewHolder.Host>(host, R.layout.list_item_demo, parent) {

        private val itemClickListener = OnClickListener { host.onDemoItemClicked(data) }

        override fun TwoLinesIconSwitchListItem.onBind() {
            icon.setImageResource(data.iconResId)
            firstLine.setText(data.titleResId)
            secondLine.setText(data.detailResId)
            setOnClickListener(itemClickListener)
        }

        interface Host {
            fun onDemoItemClicked(demoItem: DemoItem)
        }
    }
}
