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
package com.louiscad.splittiessample.sayhello

import android.content.Context
import android.text.InputType
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.extensions.ui.addDefaultAppBar
import splitties.dimensions.dip
import splitties.snackbar.snack
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.*
import splitties.views.dsl.material.MaterialComponentsStyles
import splitties.views.dsl.material.addInput
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.gravityEnd
import splitties.views.material.text
import splitties.views.onClick

class SayHelloUi(override val ctx: Context) : Ui {
    private val materialStyles = MaterialComponentsStyles(ctx)
    private val firstNameInput = materialStyles.textInputLayout.outlinedBox {
        addInput(R.id.input_name) {
            hint = "First name"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        }
    }
    private val lastNameInput = materialStyles.textInputLayout.outlinedBox {
        addInput(R.id.input_name) {
            hint = "Last name"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
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
