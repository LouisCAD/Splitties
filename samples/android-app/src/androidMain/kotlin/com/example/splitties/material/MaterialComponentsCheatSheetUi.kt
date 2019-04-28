/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.material

import android.annotation.SuppressLint
import android.content.Context
import com.example.splitties.R
import splitties.dimensions.dip
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.add
import splitties.views.dsl.core.lParams
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.verticalLayout
import splitties.views.dsl.core.verticalMargin
import splitties.views.dsl.core.wrapInScrollView
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.textAppearance
import splitties.views.verticalPadding

class MaterialComponentsCheatSheetUi(override val ctx: Context) : Ui {

    @SuppressLint("SetTextI18n")
    private val mainContent = verticalLayout {
        arrayOf(
            R.style.TextAppearance_MaterialComponents_Headline1,
            R.style.TextAppearance_MaterialComponents_Headline2,
            R.style.TextAppearance_MaterialComponents_Headline3,
            R.style.TextAppearance_MaterialComponents_Headline4,
            R.style.TextAppearance_MaterialComponents_Headline5,
            R.style.TextAppearance_MaterialComponents_Headline6,
            R.style.TextAppearance_MaterialComponents_Subtitle1,
            R.style.TextAppearance_MaterialComponents_Subtitle2,
            R.style.TextAppearance_MaterialComponents_Body1,
            R.style.TextAppearance_MaterialComponents_Body2,
            R.style.TextAppearance_MaterialComponents_Caption,
            R.style.TextAppearance_MaterialComponents_Overline,
            R.style.TextAppearance_MaterialComponents_Button
        ).forEach {
            add(textView {
                textAppearance = it
                text = resources.getResourceEntryName(it).substringAfterLast('.')
            }, lParams {
                verticalMargin = dip(4)
            })
        }
    }.wrapInScrollView(R.id.main_scrolling_content) {
        verticalPadding = dip(4)
        clipToPadding = false
    }

    override val root = coordinatorLayout {
        add(mainContent, contentScrollingWithAppBarLParams())
    }
}
