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

import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import splitties.dimensions.dip
import splitties.resources.txt
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.margin
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.v
import splitties.viewdsl.design.lParams
import splitties.views.appcompat.Toolbar
import splitties.views.imageResource
import splitties.views.setPaddingDp
import xyz.louiscad.splittiessample.R

class DemoUi(override val ctx: DemoActivity) : Ui {

    val recyclerView = v(::RecyclerView, R.id.recycler_view) {
        clipToPadding = false
        setPaddingDp(top = 8)
        isVerticalScrollBarEnabled = true
    }

    val fab = v(::FloatingActionButton) {
        imageResource = R.drawable.ic_computer_white_24dp
    }

    override val root = v(::CoordinatorLayout) {
        fitsSystemWindows = true
        add(::AppBarLayout, R.id.app_bar, R.style.AppTheme_AppBarOverlay, lParams(width = matchParent)) {
            add(::Toolbar, lParams {
                scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
            }) {
                subtitle = txt(R.string.subtitle_items_count_hint)
                popupTheme = R.style.AppTheme_PopupOverlay
                ctx.setSupportActionBar(this)
            }
        }
        add(recyclerView, lParams(width = matchParent, height = matchParent) {
            behavior = AppBarLayout.ScrollingViewBehavior()
        })
        add(fab, lParams(gravity = Gravity.BOTTOM or Gravity.END) {
            margin = dip(16)
        })
    }
}
