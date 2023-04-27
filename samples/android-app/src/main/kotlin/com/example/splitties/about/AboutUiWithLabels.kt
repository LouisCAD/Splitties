/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.about

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.Barrier
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.example.splitties.R
import com.example.splitties.extensions.ui.addDefaultAppBar
import splitties.dimensions.dip
import splitties.resources.txt
import splitties.views.dsl.constraintlayout.after
import splitties.views.dsl.constraintlayout.alignVerticallyOn
import splitties.views.dsl.constraintlayout.below
import splitties.views.dsl.constraintlayout.constraintLayout
import splitties.views.dsl.constraintlayout.endBarrier
import splitties.views.dsl.constraintlayout.lParams
import splitties.views.dsl.constraintlayout.startOfParent
import splitties.views.dsl.constraintlayout.topOfParent
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.*
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.margin
import splitties.views.textAppearance
import splitties.views.textResource
import com.google.android.material.R as MaterialR

class AboutUiWithLabels(override val ctx: Context) : Ui {

    @SuppressLint("SetTextI18n")
    private val mainContent = constraintLayout {
        val libNameLabel = label(R.string.library_name)
        val libNameTv = tv {
            textResource = R.string.lib_name
        }
        val authorLabel = label(R.string.author)
        val authorTv = tv {
            text = "Louis CAD"
        }
        val licenseLabel = label(R.string.license)
        val licenseTv = tv {
            text = "Apache v2.0"
        }
        val labelsBarrier = endBarrier(libNameLabel, authorLabel, licenseLabel)
        addLabelAndTv(labelsBarrier, libNameLabel, libNameTv) { topOfParent() }
        addLabelAndTv(labelsBarrier, authorLabel, authorTv) { below(lastChild) }
        addLabelAndTv(labelsBarrier, licenseLabel, licenseTv) { below(lastChild) }
    }

    override val root = coordinatorLayout {
        fitsSystemWindows = true
        addDefaultAppBar(ctx)
        add(mainContent, contentScrollingWithAppBarLParams {
            margin = dip(16)
        })
    }

    private fun label(@StringRes txtResId: Int) = textView {
        textAppearance = MaterialR.style.TextAppearance_MaterialComponents_Body2
        text = buildSpannedString { bold { append(txt(txtResId)) } }
    }

    private inline fun tv(initView: TextView.() -> Unit = {}) = textView {
        textAppearance = MaterialR.style.TextAppearance_MaterialComponents_Body2
        initView()
    }

    private inline fun ConstraintLayout.addLabelAndTv(
        labelBarrier: Barrier,
        label: View,
        tv: View,
        addLabelConstraints: ConstraintLayout.LayoutParams.() -> Unit
    ) {
        add(label, lParams(wrapContent, wrapContent) {
            startOfParent(); addLabelConstraints()
        })
        add(tv, lParams(wrapContent, wrapContent) {
            after(labelBarrier, margin = dip(8)); alignVerticallyOn(label)
        })
    }
}

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class AboutUiWithLabelsPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = { AboutUiWithLabels(it) }
)
//endregion
