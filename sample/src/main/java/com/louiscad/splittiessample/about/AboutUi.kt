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
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.extensions.ui.addDefaultAppBar
import splitties.dimensions.dip
import splitties.views.centerText
import splitties.views.dsl.constraintlayout.*
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.add
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.wrapContent
import splitties.views.dsl.design.contentScrollingWithAppBarLParams
import splitties.views.dsl.design.coordinatorLayout
import splitties.views.textAppearance
import splitties.views.textResource

class AboutUi(override val ctx: Context) : Ui {

    private val mainContent = constraintLayout {
        val headlineTv = add(textView {
            textAppearance = R.style.TextAppearance_AppCompat_Headline
            textResource = R.string.lib_name
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topOfParent()
            topMargin = dip(16)
        })
        val authorTv = add(textView {
            textAppearance = R.style.TextAppearance_AppCompat_Small
            textResource = R.string.a_lib_by_louiscad
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topToBottomOf(headlineTv)
            topMargin = dip(8)
        })
        add(textView {
            textAppearance = R.style.TextAppearance_AppCompat_Caption
            textResource = R.string.licensed_under_apache_2
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topToBottomOf(authorTv)
            topMargin = dip(8)
        })
    }

    override val root = coordinatorLayout {
        fitsSystemWindows = true
        addDefaultAppBar(ctx)
        add(mainContent, contentScrollingWithAppBarLParams())
    }
}
