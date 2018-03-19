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

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import splitties.dimensions.dip
import splitties.resources.txt
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.margin
import splitties.viewdsl.core.v
import splitties.viewdsl.design.appBarLParams
import splitties.viewdsl.design.contentScrollingWithAppBarLParams
import splitties.viewdsl.design.defaultLParams
import splitties.viewdsl.recyclerview.recyclerView
import splitties.views.appcompat.Toolbar
import splitties.views.imageResource
import splitties.views.setPaddingDp
import xyz.louiscad.splittiessample.R

class DemoUi(override val ctx: Context, host: Host) : Ui {

    interface Host : DemoAdapter.DemoViewHolder.Host

    val demoListView = v(::recyclerView, R.id.recycler_view) {
        clipToPadding = false
        setPaddingDp(top = 8)
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        adapter = DemoAdapter(layoutManager, host)
    }

    val fab = v(::FloatingActionButton) {
        imageResource = R.drawable.ic_computer_white_24dp
    }

    override val root = v(::CoordinatorLayout) {
        fitsSystemWindows = true
        add(::AppBarLayout, R.id.app_bar, R.style.AppTheme_AppBarOverlay, appBarLParams()) {
            add(::Toolbar, defaultLParams()) {
                subtitle = txt(R.string.subtitle_items_count_hint)
                popupTheme = R.style.AppTheme_PopupOverlay
                (ctx as? AppCompatActivity)?.setSupportActionBar(this)
            }
        }
        add(demoListView, contentScrollingWithAppBarLParams())
        add(fab, defaultLParams(gravity = Gravity.BOTTOM or Gravity.END) {
            margin = dip(16)
        })
    }
}
