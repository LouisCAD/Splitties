/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.about

import android.content.Context
import android.util.AttributeSet
import com.example.splitties.R
import com.example.splitties.extensions.ui.addDefaultAppBar
import splitties.dimensions.dip
import splitties.views.centerText
import splitties.views.dsl.constraintlayout.*
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.*
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.textAppearance
import splitties.views.textResource
import com.google.android.material.R as MaterialR

class AboutUi(override val ctx: Context) : Ui {

    private val mainContent = constraintLayout {
        val headlineTv = add(textView {
            textAppearance = MaterialR.style.TextAppearance_AppCompat_Headline
            textResource = R.string.lib_name
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topOfParent(margin = dip(16))
        })
        val authorTv = add(textView {
            textAppearance = MaterialR.style.TextAppearance_AppCompat_Small
            textResource = R.string.a_lib_by_louiscad
            centerText()
        }, lParams(height = wrapContent) {
            centerHorizontally(); topToBottomOf(headlineTv, margin = dip(8))
        })
        add(textView {
            textAppearance = MaterialR.style.TextAppearance_AppCompat_Caption
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

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class AboutUiPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = { AboutUi(it) }
)
//endregion
