/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.sayhello

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.splitties.R
import com.example.splitties.extensions.ui.addDefaultAppBar
import splitties.dimensions.dip
import splitties.experimental.ExperimentalSplittiesApi
import splitties.snackbar.snack
import splitties.views.InputType
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.*
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.dsl.material.MaterialComponentsStyles
import splitties.views.dsl.material.addInput
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.gravityEnd
import splitties.views.margin
import splitties.views.material.text
import splitties.views.onClick
import splitties.views.type

@OptIn(ExperimentalSplittiesApi::class)
class SayHelloUi(override val ctx: Context) : Ui {
    private val materialStyles = MaterialComponentsStyles(ctx)
    private val firstNameInput = materialStyles.textInputLayout.outlinedBox {
        addInput(R.id.input_name) {
            hint = "First name"
            type = InputType.personName
        }
    }
    private val lastNameInput = materialStyles.textInputLayout.outlinedBox {
        addInput(R.id.input_name) {
            hint = "Last name"
            type = InputType.personName
        }
    }
    private val sayHelloBtn = materialStyles.button.filled { text = "Say hello!" }
    override val root: CoordinatorLayout = coordinatorLayout {
        fitsSystemWindows = true
        addDefaultAppBar(ctx)
        add(verticalLayout {
            add(firstNameInput, lParams(width = matchParent))
            add(lastNameInput, lParams(width = matchParent))
            add(sayHelloBtn, lParams(gravity = gravityEnd) {
                topMargin = dip(8)
            })
        }, contentScrollingWithAppBarLParams {
            margin = dip(16)
        })
    }

    init {
        sayHelloBtn.onClick {
            val fullName = "${firstNameInput.text} ${lastNameInput.text}"
            root.snack("Hello $fullName!")
        }
    }
}

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class SayHelloUiPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = { SayHelloUi(it) }
)
//endregion
