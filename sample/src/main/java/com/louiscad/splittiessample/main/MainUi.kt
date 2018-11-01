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

package com.louiscad.splittiessample.main

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.support.v7.app.AppCompatActivity
import com.louiscad.splittiessample.R
import splitties.dimensions.dip
import splitties.resources.dimenPxSize
import splitties.resources.styledColor
import splitties.resources.styledColorSL
import splitties.viewdsl.appcompat.AppCompatStyles
import splitties.viewdsl.appcompat.toolbar
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.button
import splitties.viewdsl.core.lParams
import splitties.viewdsl.core.margin
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.styles.invoke
import splitties.viewdsl.core.textView
import splitties.viewdsl.core.verticalLayout
import splitties.viewdsl.design.EXIT_UNTIL_COLLAPSED
import splitties.viewdsl.design.PIN
import splitties.viewdsl.design.SCROLL
import splitties.viewdsl.design.actionBarLParams
import splitties.viewdsl.design.anchorTo
import splitties.viewdsl.design.appBarLParams
import splitties.viewdsl.design.appBarLayout
import splitties.viewdsl.design.collapsingToolbarLayout
import splitties.viewdsl.design.contentScrollingWithAppBarLParams
import splitties.viewdsl.design.coordinatorLayout
import splitties.viewdsl.design.defaultLParams
import splitties.viewdsl.design.floatingActionButton
import splitties.viewdsl.recyclerview.wrapInRecyclerView
import splitties.views.design.contentScrimColor
import splitties.views.gravityCenterHorizontal
import splitties.views.gravityEndBottom
import splitties.views.imageResource
import splitties.views.setCompoundDrawables
import splitties.views.textResource

class MainUi(override val ctx: Context) : Ui {

    private val appCompatStyles = AppCompatStyles(ctx)

    val launchDemoBtn = appCompatStyles.button.flatColored(ctx) {
        textResource = R.string.go_to_the_demo
    }
    val bePoliteBtn = appCompatStyles.button.colored(ctx) {
        textResource = R.string.be_polite
    }
    val toggleNightModeBtn = button {
        compoundDrawablePadding = dip(4)
        if (SDK_INT >= 23) compoundDrawableTintList = styledColorSL(android.R.attr.textColorSecondary)
        setCompoundDrawables(start = R.drawable.ic_invert_colors_white_24dp)
        textResource = R.string.toggle_night_mode
    }
    val fab = floatingActionButton {
        imageResource = R.drawable.ic_favorite_white_24dp
    }
    val content = verticalLayout {
        add(launchDemoBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(bePoliteBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(toggleNightModeBtn, lParams {
            gravity = gravityCenterHorizontal
            bottomMargin = dip(8)
        })
        add(textView {
            textResource = R.string.large_text
        }, lParams {
            margin = dimenPxSize(R.dimen.text_margin)
        })
    }.wrapInRecyclerView()
    private val appBar = appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
        add(collapsingToolbarLayout {
            fitsSystemWindows = true
            contentScrimColor = styledColor(R.attr.colorPrimary)
            add(toolbar {
                (ctx as? AppCompatActivity)?.setSupportActionBar(this)
                popupTheme = R.style.AppTheme_PopupOverlay
            }, actionBarLParams(collapseMode = PIN))
        }, defaultLParams(height = matchParent) {
            scrollFlags = SCROLL or EXIT_UNTIL_COLLAPSED
        })
    }
    override val root = coordinatorLayout {
        fitsSystemWindows = true
        add(appBar, appBarLParams(dip(180)))
        add(content, contentScrollingWithAppBarLParams())
        add(fab, defaultLParams {
            anchorTo(appBar, gravity = gravityEndBottom)
            margin = dip(16)
        })
    }
}
