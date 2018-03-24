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

package com.louiscad.splittiessample.about

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import com.louiscad.splittiessample.R
import splitties.dimensions.dip
import splitties.viewdsl.appcompat.textView
import splitties.viewdsl.constraintlayout.centerHorizontally
import splitties.viewdsl.constraintlayout.lParams
import splitties.viewdsl.constraintlayout.topOfParent
import splitties.viewdsl.constraintlayout.topToBottomOf
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.v
import splitties.viewdsl.core.wrapContent
import splitties.viewdsl.design.appBarLParams
import splitties.viewdsl.design.contentScrollingWithAppBarLParams
import splitties.viewdsl.design.defaultLParams
import splitties.views.appcompat.Toolbar
import splitties.views.centerText
import splitties.views.textAppearance
import splitties.views.textResource

class AboutUi(override val ctx: Context) : Ui {

    private val mainContent = v(::ConstraintLayout) {
        val headlineTv = add(::textView, R.id.tv_headline, lParams(height = wrapContent) {
            topOfParent()
            centerHorizontally()
            topMargin = dip(16)
        }) {
            textAppearance = R.style.TextAppearance_AppCompat_Headline
            textResource = R.string.lib_name
            centerText()
        }
        val authorTv = add(::textView, R.id.tv_author, lParams(height = wrapContent) {
            topToBottomOf(headlineTv)
            centerHorizontally()
            topMargin = dip(8)
        }) {
            textAppearance = R.style.TextAppearance_AppCompat_Small
            textResource = R.string.a_lib_by_louiscad
            centerText()
        }
        add(::textView, R.id.tv_license_name, lParams(height = wrapContent) {
            topToBottomOf(authorTv)
            centerHorizontally()
            topMargin = dip(8)
        }) {
            textAppearance = R.style.TextAppearance_AppCompat_Caption
            textResource = R.string.licensed_under_apache_2
            centerText()
        }
    }

    override val root = v(::CoordinatorLayout) {
        fitsSystemWindows = true
        add(::AppBarLayout, R.id.app_bar, R.style.AppTheme_AppBarOverlay, appBarLParams()) {
            add(::Toolbar, defaultLParams()) {
                popupTheme = R.style.AppTheme_PopupOverlay
                (ctx as? AppCompatActivity)?.setSupportActionBar(this)
            }
        }
        add(mainContent, contentScrollingWithAppBarLParams())
    }
}
