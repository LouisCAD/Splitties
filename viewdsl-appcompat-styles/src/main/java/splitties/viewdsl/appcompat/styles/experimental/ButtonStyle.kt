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
import android.util.Log
import android.widget.Button
import splitties.dimensions.dip
import splitties.viewdsl.appcompat.styles.R
import splitties.viewdsl.core.experimental.styles.MutatingStyle
import splitties.viewdsl.core.experimental.styles.Style
import splitties.views.gravityCenterHorizontal
import splitties.views.gravityCenterVertical
import splitties.views.textAppearance
import kotlin.system.measureNanoTime

object ButtonStyle : MutatingStyle<Button> {
    val colored: Style<Button> get() = Colored

    @RequiresApi(LOLLIPOP) private var buttonStateListAnimMaterialResId = 0

    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
        textAppearance = R.style.TextAppearance_AppCompat_Widget_Button
        minHeight = dip(48)
        minWidth = dip(88)
        if (SDK_INT >= LOLLIPOP) {
                if (buttonStateListAnimMaterialResId == 0) {
                    buttonStateListAnimMaterialResId = resources.getIdentifier("button_state_list_anim_material", "anim", "android")
                }
            val lookupTime = measureNanoTime {
                val id = buttonStateListAnimMaterialResId
                if (id != 0) AnimatorInflater.loadStateListAnimator(context, id)
            }
            val copiedTime = measureNanoTime {
                AnimatorInflater.loadStateListAnimator(context, R.animator.button_state_list_anim_material)
            }
            Log.i("ButtonStyle", "lookupTime: $lookupTime")
            Log.i("ButtonStyle", "copiedTime: $copiedTime")
            val id = buttonStateListAnimMaterialResId
            if (id != 0) stateListAnimator = AnimatorInflater.loadStateListAnimator(context, id)
            //stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.animator.button_state_list_anim_material)
        }
        isFocusable = true
        isClickable = true
        gravity = gravityCenterVertical or gravityCenterHorizontal
    }

    internal fun Button.applyCommonStyle() {
        TODO()
    }
}

private object Colored : MutatingStyle<Button> {
    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button_Colored // is the cloned theme
        with(ButtonStyle) { applyStyle() }
        setBackgroundResource(R.drawable.abc_btn_colored_material)
        textAppearance = R.style.TextAppearance_AppCompat_Widget_Button_Colored
    }
}
