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
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.louiscad.splittiessample.R
import splitties.dimensions.dip
import splitties.resources.dimenPxSize
import splitties.resources.styledColor
import splitties.resources.styledColorSL
import splitties.views.design.contentScrimColor
import splitties.views.dsl.appcompat.AppCompatStyles
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.add
import splitties.views.dsl.core.button
import splitties.views.dsl.core.lParams
import splitties.views.dsl.core.margin
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.styles.invoke
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.verticalLayout
import splitties.views.dsl.design.EXIT_UNTIL_COLLAPSED
import splitties.views.dsl.design.PIN
import splitties.views.dsl.design.SCROLL
import splitties.views.dsl.design.actionBarLParams
import splitties.views.dsl.design.anchorTo
import splitties.views.dsl.design.appBarLParams
import splitties.views.dsl.design.appBarLayout
import splitties.views.dsl.design.collapsingToolbarLayout
import splitties.views.dsl.design.contentScrollingWithAppBarLParams
import splitties.views.dsl.design.coordinatorLayout
import splitties.views.dsl.design.defaultLParams
import splitties.views.dsl.design.floatingActionButton
import splitties.views.dsl.recyclerview.wrapInRecyclerView
import splitties.views.gravityCenterHorizontal
import splitties.views.gravityEndBottom
import splitties.views.imageResource
import splitties.views.setCompoundDrawables
import splitties.views.textResource

class MainUi(override val ctx: Context) : Ui {

    val launchDemoBtn: Button
    val bePoliteWithPermissionsBtn: Button
    val sayHelloBtn: Button
    val toggleNightModeBtn: Button
    val fab: FloatingActionButton
    private val appBar: AppBarLayout

    private val appCompatStyles = AppCompatStyles(ctx)
    override val root = coordinatorLayout {
        fitsSystemWindows = true
        appBar = add(appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
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
        }, appBarLParams(dip(180)))
        add(verticalLayout {
            launchDemoBtn = add(appCompatStyles.button.flatColored(ctx) {
                textResource = R.string.go_to_the_demo
            }, lParams {
                gravity = gravityCenterHorizontal
                topMargin = dip(8)
            })
            bePoliteWithPermissionsBtn = add(appCompatStyles.button.colored(ctx) {
                textResource = R.string.be_polite_with_permissions
            }, lParams {
                gravity = gravityCenterHorizontal
                topMargin = dip(8)
            })
            sayHelloBtn = add(appCompatStyles.button.flat(ctx) {
                textResource = R.string.say_hello
            }, lParams {
                gravity = gravityCenterHorizontal
                topMargin = dip(8)
            })
            toggleNightModeBtn = add(button {
                compoundDrawablePadding = dip(4)
                if (SDK_INT >= 23) compoundDrawableTintList = styledColorSL(android.R.attr.textColorSecondary)
                setCompoundDrawables(start = R.drawable.ic_invert_colors_white_24dp)
                textResource = R.string.toggle_night_mode
            }, lParams {
                gravity = gravityCenterHorizontal
                bottomMargin = dip(8)
            })
            add(textView {
                textResource = R.string.large_text
            }, lParams {
                margin = dimenPxSize(R.dimen.text_margin)
            })
        }.wrapInRecyclerView(), contentScrollingWithAppBarLParams())
        fab = add(floatingActionButton {
            imageResource = R.drawable.ic_favorite_white_24dp
        }, defaultLParams {
            anchorTo(appBar, gravity = gravityEndBottom)
            margin = dip(16)
        })
    }
}
