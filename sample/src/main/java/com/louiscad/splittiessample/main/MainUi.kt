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
import splitties.views.*
import splitties.views.design.contentScrimColor
import splitties.views.dsl.appcompat.AppCompatStyles
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.core.*
import splitties.views.dsl.core.styles.invoke
import splitties.views.dsl.design.*
import splitties.views.dsl.recyclerview.wrapInRecyclerView

class MainUi(override val ctx: Context) : Ui {

    private val appCompatStyles = AppCompatStyles(ctx)

    val launchDemoBtn = appCompatStyles.button.flatColored(ctx) {
        textResource = R.string.go_to_the_demo
    }
    val bePoliteWithPermissionsBtn = appCompatStyles.button.colored(ctx) {
        textResource = R.string.be_polite_with_permissions
    }
    val sayHelloBtn = appCompatStyles.button.flat(ctx) {
        textResource = R.string.say_hello
    }
    val toggleNightModeBtn = button {
        compoundDrawablePadding = dip(4)
        if (SDK_INT >= 23) {
            compoundDrawableTintList = styledColorSL(android.R.attr.textColorSecondary)
        }
        setCompoundDrawables(start = R.drawable.ic_invert_colors_white_24dp)
        textResource = R.string.toggle_night_mode
    }
    val fab = floatingActionButton {
        imageResource = R.drawable.ic_favorite_white_24dp
    }
    private val content = verticalLayout {
        add(launchDemoBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(bePoliteWithPermissionsBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(sayHelloBtn, lParams {
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
