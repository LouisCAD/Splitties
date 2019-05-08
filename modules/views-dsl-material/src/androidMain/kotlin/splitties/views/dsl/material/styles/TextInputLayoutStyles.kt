/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.material.styles

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.material.textfield.TextInputLayout
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.styles.styledView
import splitties.views.dsl.material.R

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class TextInputLayoutStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun filledBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextInputLayout.() -> Unit = {}
    ): TextInputLayout = ctx.styledView(
        newViewRef = ::TextInputLayout,
        styleAttr = R.attr.Widget_MaterialComponents_TextInputLayout_FilledBox,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun filledBoxDense(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextInputLayout.() -> Unit = {}
    ): TextInputLayout = ctx.styledView(
        newViewRef = ::TextInputLayout,
        styleAttr = R.attr.Widget_MaterialComponents_TextInputLayout_FilledBox_Dense,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun outlinedBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextInputLayout.() -> Unit = {}
    ): TextInputLayout = ctx.styledView(
        newViewRef = ::TextInputLayout,
        styleAttr = R.attr.Widget_MaterialComponents_TextInputLayout_OutlinedBox,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun outlinedBoxDense(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextInputLayout.() -> Unit = {}
    ): TextInputLayout = ctx.styledView(
        newViewRef = ::TextInputLayout,
        styleAttr = R.attr.Widget_MaterialComponents_TextInputLayout_OutlinedBox_Dense,
        id = id,
        theme = theme,
        initView = initView
    )
}
