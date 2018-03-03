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

package xyz.louiscad.splittiessample.main

import android.os.Build.VERSION.SDK_INT
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView
import android.view.Gravity
import splitties.dimensions.dip
import splitties.resources.dimenPxSize
import splitties.resources.styledColor
import splitties.resources.styledColorSL
import splitties.resources.styledDimenPxSize
import splitties.viewdsl.appcompat.button
import splitties.viewdsl.appcompat.styles.coloredButton
import splitties.viewdsl.appcompat.textView
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.lParams
import splitties.viewdsl.core.margin
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.v
import splitties.viewdsl.core.verticalLayout
import splitties.viewdsl.design.EXIT_UNTIL_COLLAPSED
import splitties.viewdsl.design.PIN
import splitties.viewdsl.design.SCROLL
import splitties.viewdsl.design.defaultLParams
import splitties.views.appcompat.Toolbar
import splitties.views.imageResource
import splitties.views.setCompoundDrawables
import splitties.views.textResource
import xyz.louiscad.splittiessample.R

class MainUi(override val ctx: MainActivity) : Ui {

    val launchDemoBtn = v(::coloredButton) {
        textResource = R.string.go_to_the_demo
    }

    val toggleNightModeBtn = v(::button) {
        compoundDrawablePadding = dip(4)
        if (SDK_INT >= 23) compoundDrawableTintList = styledColorSL(android.R.attr.textColorSecondary)
        setCompoundDrawables(start = R.drawable.ic_invert_colors_white_24dp)
        textResource = R.string.toggle_night_mode
    }

    val fab = v(::FloatingActionButton) {
        imageResource = R.drawable.ic_favorite_white_24dp
    }

    val content = v(::NestedScrollView) {
        add(::verticalLayout, lParams(width = matchParent)) {
            add(launchDemoBtn, lParams {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dip(8)
            })
            add(toggleNightModeBtn, lParams {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = dip(8)
            })
            add(::textView, lParams {
                margin = dimenPxSize(R.dimen.text_margin)
            }) {
                textResource = R.string.large_text
            }
        }
    }

    override val root = v(::CoordinatorLayout) {
        fitsSystemWindows = true
        add(::AppBarLayout, R.id.app_bar, R.style.AppTheme_AppBarOverlay,
                defaultLParams(width = matchParent, height = dip(180))) {
            add(::CollapsingToolbarLayout, defaultLParams(height = matchParent) {
                scrollFlags = SCROLL or EXIT_UNTIL_COLLAPSED
            }) {
                fitsSystemWindows = true
                setContentScrimColor(styledColor(R.attr.colorPrimary))
                val actionBarSize = styledDimenPxSize(R.attr.actionBarSize)
                add(::Toolbar, defaultLParams(height = actionBarSize, collapseMode = PIN)) {
                    ctx.setSupportActionBar(this)
                    popupTheme = R.style.AppTheme_PopupOverlay
                }
            }
        }
        add(content, defaultLParams(width = matchParent, height = matchParent) {
            behavior = AppBarLayout.ScrollingViewBehavior()
        })
        add(fab, defaultLParams {
            anchorId = R.id.app_bar
            anchorGravity = Gravity.BOTTOM or Gravity.END
            margin = dip(16)
        })
    }
}
