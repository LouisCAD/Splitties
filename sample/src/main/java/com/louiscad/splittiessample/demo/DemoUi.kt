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
import android.support.v7.app.AppCompatActivity
import com.louiscad.splittiessample.R
import splitties.dimensions.dip
import splitties.resources.txt
import splitties.viewdsl.appcompat.toolbar
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.margin
import splitties.viewdsl.design.appBarLParams
import splitties.viewdsl.design.appBarLayout
import splitties.viewdsl.design.contentScrollingWithAppBarLParams
import splitties.viewdsl.design.coordinatorLayout
import splitties.viewdsl.design.defaultLParams
import splitties.viewdsl.design.floatingActionButton
import splitties.viewdsl.recyclerview.experimental.recyclerView
import splitties.views.gravityEndBottom
import splitties.views.imageResource
import splitties.views.setPaddingDp

class DemoUi(override val ctx: Context, host: Host) : Ui {
    interface Host : DemoAdapter.DemoViewHolder.Host

    val demoListView = recyclerView(R.id.recycler_view) {
        clipToPadding = false
        setPaddingDp(top = 8)
        setHasFixedSize(true)
        val demoAdapter = DemoAdapter(host)
        adapter = demoAdapter
        layoutManager = demoAdapter.layoutManager
    }
    val fab = floatingActionButton {
        imageResource = R.drawable.ic_computer_white_24dp
    }
    override val root = coordinatorLayout {
        fitsSystemWindows = true
        add(appBarLayout(R.id.app_bar, R.style.AppTheme_AppBarOverlay) {
            add(toolbar {
                subtitle = txt(R.string.subtitle_items_count_hint)
                popupTheme = R.style.AppTheme_PopupOverlay
                (ctx as? AppCompatActivity)?.setSupportActionBar(this)
            }, defaultLParams())
        }, appBarLParams())
        add(demoListView, contentScrollingWithAppBarLParams())
        add(fab, defaultLParams(gravity = gravityEndBottom) {
            margin = dip(16)
        })
    }
}
