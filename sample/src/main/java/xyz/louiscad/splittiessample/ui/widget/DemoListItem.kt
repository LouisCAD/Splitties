/*
 * Copyright (c) 2016. Louis Cognault Ayeva Derman
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

package xyz.louiscad.splittiessample.ui.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.list_item_two_lines_icon.view.*
import xyz.louiscad.splittiessample.ui.model.DemoItem
import xyz.louiscad.typesaferecyclerview.util.ViewWrapper

/**
 * As you can see, this View is made to be used with [DemoItem] as a [RecyclerView]
 * list item.
 */
class DemoListItem : TwoLinesIconListItem, ViewWrapper.Binder<DemoItem, DemoListItem.Host>, View.OnClickListener {

    private lateinit var mHost: Host
    private lateinit var mDemoItem: DemoItem

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun bind(demoItem: DemoItem) {
        mDemoItem = demoItem
        icon.setImageResource(demoItem.iconResId)
        firstLine.setText(demoItem.titleResId)
        secondLine.setText(demoItem.detailResId)
        setOnClickListener(this)
    }

    override fun onClick(v: View) {
        mHost.onDemoItemClicked(mDemoItem)
    }

    override fun setHost(host: Host) {
        mHost = host
    }

    interface Host {
        fun onDemoItemClicked(demoItem: DemoItem)
    }
}
