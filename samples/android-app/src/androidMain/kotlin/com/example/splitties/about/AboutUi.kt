/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.about

import android.content.Context
import com.example.splitties.R
import com.example.splitties.extensions.ui.addDefaultAppBar
import splitties.dimensions.dip
import splitties.views.centerText
import splitties.views.dsl.constraintlayout.*
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.add
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.wrapContent
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.textAppearance
import splitties.views.textResource

class AboutUi(override val ctx: Context) : Ui {

    private val mainContent = constraintLayout {
        val headlineTv = add(textView {
            textAppearance = R.style.TextAppearance_AppCompat_Headline
            textResource = R.string.lib_name
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topOfParent(margin = dip(16))
        })
        val authorTv = add(textView {
            textAppearance = R.style.TextAppearance_AppCompat_Small
            textResource = R.string.a_lib_by_louiscad
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topToBottomOf(headlineTv, margin = dip(8))
        })
        add(textView {
            textAppearance = R.style.TextAppearance_AppCompat_Caption
            textResource = R.string.licensed_under_apache_2
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topToBottomOf(authorTv, margin = dip(8))
        })
    }

    override val root = coordinatorLayout {
        fitsSystemWindows = true
        addDefaultAppBar(ctx)
        add(mainContent, contentScrollingWithAppBarLParams())
    }
}
