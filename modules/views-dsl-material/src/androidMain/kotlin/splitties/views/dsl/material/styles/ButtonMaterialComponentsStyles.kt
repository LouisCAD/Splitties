/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.material.styles

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.material.button.MaterialButton
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.styles.styledView
import splitties.views.dsl.material.R

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ButtonMaterialComponentsStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun filled(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun filledWithIcon(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_Icon,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun filledUnelevated(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_UnelevatedButton,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun filledUnelevatedWithIcon(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_UnelevatedButton_Icon,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun outlined(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_OutlinedButton,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun outlinedWithIcon(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_OutlinedButton_Icon,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun text(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_TextButton,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun textWithIcon(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_TextButton_Icon,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun textDialog(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_TextButton_Dialog,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun textDialogWithIcon(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MaterialButton.() -> Unit = {}
    ): MaterialButton = ctx.styledView(
        newViewRef = ::MaterialButton,
        styleAttr = R.attr.Widget_MaterialComponents_Button_TextButton_Dialog_Icon,
        id = id,
        theme = theme,
        initView = initView
    )
}
