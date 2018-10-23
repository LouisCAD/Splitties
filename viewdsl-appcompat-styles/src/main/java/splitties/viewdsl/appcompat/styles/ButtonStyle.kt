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
package splitties.viewdsl.appcompat.styles

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.annotation.RequiresApi
import android.widget.Button
import splitties.dimensions.dip
import splitties.resources.dimenPxSize
import splitties.viewdsl.core.Style
import splitties.views.gravityCenterHorizontal
import splitties.views.gravityCenterVertical
import splitties.views.textAppearance

object ButtonStyle : Style<Button> {
    inline val colored: Style<Button> get() = Colored
    inline val flat: Style<Button> get() = Borderless
    inline val flatColored: Style<Button> get() = BorderlessColored
    inline val alertDialogButtonBar: Style<Button> get() = ButtonBarAlertDialog

    @RequiresApi(LOLLIPOP) private var buttonStateListAnimMaterialResId = 0

    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button // is the cloned theme for reference
        setBackgroundResource(R.drawable.abc_btn_default_mtrl_shape)
        applyCommonStyle(useDefaultTextAppearance = true)
    }

    internal fun Button.applyCommonStyle(
            enableStateListAnimator: Boolean = true,
            useDefaultTextAppearance: Boolean = false
    ) {
        if (useDefaultTextAppearance) textAppearance = R.style.TextAppearance_AppCompat_Widget_Button
        minHeight = dip(48)
        minWidth = dip(88)
        if (SDK_INT >= LOLLIPOP && enableStateListAnimator) {
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

@PublishedApi
internal object Colored : Style<Button> {
    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button_Colored // is the cloned theme for reference
        with(ButtonStyle) { applyCommonStyle() }
        setBackgroundResource(R.drawable.abc_btn_colored_material)
        textAppearance = R.style.TextAppearance_AppCompat_Widget_Button_Colored
    }
}

@PublishedApi
internal object Borderless : Style<Button> {
    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button_Borderless // is the cloned theme for reference
        with(ButtonStyle) {
            applyCommonStyle(enableStateListAnimator = false, useDefaultTextAppearance = true)
        }
        setBackgroundResource(R.drawable.abc_btn_borderless_material)
    }
}

@PublishedApi
internal object BorderlessColored : Style<Button> {
    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button_Borderless_Colored // is the cloned theme for reference
        with(ButtonStyle) { applyCommonStyle(enableStateListAnimator = false) }
        setBackgroundResource(R.drawable.abc_btn_borderless_material)
        textAppearance = R.style.TextAppearance_AppCompat_Widget_Button_Borderless_Colored
    }
}

@PublishedApi
internal object ButtonBarAlertDialog : Style<Button> {
    @SuppressLint("PrivateResource")
    override fun Button.applyStyle() {
        R.style.Widget_AppCompat_Button_ButtonBar_AlertDialog // is the cloned theme for reference
        with(BorderlessColored) { applyStyle() }
        minWidth = dip(64)
        minHeight = dimenPxSize(R.dimen.abc_alert_dialog_button_bar_height)
    }
}
