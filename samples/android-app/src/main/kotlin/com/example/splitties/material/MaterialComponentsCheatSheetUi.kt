/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.material

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.example.splitties.R
import splitties.dimensions.dip
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.*
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.textAppearance
import splitties.views.verticalMargin
import splitties.views.verticalPadding
import com.google.android.material.R as MaterialR

class MaterialComponentsCheatSheetUi(override val ctx: Context) : Ui {

    @SuppressLint("SetTextI18n")
    private val mainContent = verticalLayout {
        arrayOf(
            MaterialR.style.TextAppearance_MaterialComponents_Headline1,
            MaterialR.style.TextAppearance_MaterialComponents_Headline2,
            MaterialR.style.TextAppearance_MaterialComponents_Headline3,
            MaterialR.style.TextAppearance_MaterialComponents_Headline4,
            MaterialR.style.TextAppearance_MaterialComponents_Headline5,
            MaterialR.style.TextAppearance_MaterialComponents_Headline6,
            MaterialR.style.TextAppearance_MaterialComponents_Subtitle1,
            MaterialR.style.TextAppearance_MaterialComponents_Subtitle2,
            MaterialR.style.TextAppearance_MaterialComponents_Body1,
            MaterialR.style.TextAppearance_MaterialComponents_Body2,
            MaterialR.style.TextAppearance_MaterialComponents_Caption,
            MaterialR.style.TextAppearance_MaterialComponents_Overline,
            MaterialR.style.TextAppearance_MaterialComponents_Button
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

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class MaterialComponentsCheatSheetUiPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = { MaterialComponentsCheatSheetUi(it) }
)
//endregion
