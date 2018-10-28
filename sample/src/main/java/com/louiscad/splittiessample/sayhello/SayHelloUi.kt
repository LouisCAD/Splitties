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
import android.support.design.widget.CoordinatorLayout
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.extensions.ui.addDefaultAppBar
import splitties.snackbar.snack
import splitties.viewdsl.appcompat.styles.AppCompatStyles
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.add
import splitties.viewdsl.core.editText
import splitties.viewdsl.core.invoke
import splitties.viewdsl.core.lParams
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.verticalLayout
import splitties.viewdsl.design.contentScrollingWithAppBarLParams
import splitties.viewdsl.design.coordinatorLayout
import splitties.views.gravityEnd
import splitties.views.onClick

class SayHelloUi(override val ctx: Context) : Ui {
    private val appCompatStyles = AppCompatStyles()
    private val nameInput = editText(R.id.input_name) {
        hint = "Your name"
    }
    private val sayHelloBtn = appCompatStyles.button.colored(ctx) { text = "Say hello!" }
    override val root: CoordinatorLayout = coordinatorLayout {
        fitsSystemWindows = true
        addDefaultAppBar(ctx)
        add(verticalLayout {
            add(nameInput, lParams(width = matchParent))
            add(sayHelloBtn, lParams(gravity = gravityEnd))
        }, contentScrollingWithAppBarLParams())
    }

    init {
        sayHelloBtn.onClick {
            root.snack("Hello ${nameInput.text}!")
        }
    }
}
