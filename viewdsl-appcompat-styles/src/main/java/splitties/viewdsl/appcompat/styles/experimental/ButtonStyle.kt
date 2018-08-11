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
package splitties.viewdsl.appcompat.styles.experimental

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.annotation.RequiresApi
import android.widget.Button
import splitties.dimensions.dip
import splitties.viewdsl.appcompat.styles.R
import splitties.viewdsl.core.experimental.styles.MutatingStyle
import splitties.viewdsl.core.experimental.styles.Style
import splitties.views.gravityCenterHorizontal
import splitties.views.gravityCenterVertical
import splitties.views.textAppearance

object ButtonStyle : MutatingStyle<Button> {
    val colored: Style<Button> get() = Colored

    @RequiresApi(LOLLIPOP) private var buttonStateListAnimMaterialResId = 0

    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button // is the cloned theme for reference
        setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
        textAppearance = R.style.TextAppearance_AppCompat_Widget_Button
        applyCommonStyle()
    }

    internal fun Button.applyCommonStyle() {
        minHeight = dip(48)
        minWidth = dip(88)
        if (SDK_INT >= LOLLIPOP) {
            if (buttonStateListAnimMaterialResId == 0) buttonStateListAnimMaterialResId = resources
                    .getIdentifier("button_state_list_anim_material", "anim", "android")
            val id = buttonStateListAnimMaterialResId
            if (id != 0) stateListAnimator = AnimatorInflater.loadStateListAnimator(context, id)
        }
        isFocusable = true
        isClickable = true
        gravity = gravityCenterVertical or gravityCenterHorizontal
    }
}

private object Colored : MutatingStyle<Button> {
    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button_Colored // is the cloned theme for reference
        with(ButtonStyle) { applyCommonStyle() }
        setBackgroundResource(R.drawable.abc_btn_colored_material)
        textAppearance = R.style.TextAppearance_AppCompat_Widget_Button_Colored
    }
}
