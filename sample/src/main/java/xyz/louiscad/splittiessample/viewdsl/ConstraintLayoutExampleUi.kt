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

package xyz.louiscad.splittiessample.viewdsl

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
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
import splitties.views.centerText
import splitties.views.textAppearance
import xyz.louiscad.splittiessample.R

class ConstraintLayoutExampleUi(override val ctx: Context) : Ui {

    override val root = v(::ConstraintLayout) {
        val firstLine = add(::textView, R.id.firstLine, lParams(height = wrapContent) {
            topOfParent()
            centerHorizontally()
            topMargin = dip(16)
        }) {
            textAppearance = R.style.TextAppearance_AppCompat_Headline
            @SuppressLint("SetTextI18n")
            text = "Splitties"
            centerText()
        }
        add(::textView, R.id.secondLine, lParams(height = wrapContent) {
            topToBottomOf(firstLine)
            centerHorizontally()
            topMargin = dip(8)
        }) {
            textAppearance = R.style.TextAppearance_AppCompat_Caption
            @SuppressLint("SetTextI18n")
            text = "A library by Louis CAD"
            centerText()
        }
    }
}
