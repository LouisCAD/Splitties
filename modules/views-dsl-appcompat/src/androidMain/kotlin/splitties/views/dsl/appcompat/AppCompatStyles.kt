/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.appcompat

import android.content.Context
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.*
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.styles.styledView

class AppCompatStyles(@PublishedApi internal val ctx: Context) {
    init {
        ctx.theme.applyStyle(R.style.AppCompatStyles, false)
    }

    inline val button get() = ButtonAppCompatStyles(ctx)
    inline val seekBar get() = SeekBarAppCompatStyles(ctx)
    inline val spinner get() = SpinnerAppCompatStyles(ctx)
    inline val textView get() = TextViewAppCompatStyles(ctx)
    inline fun actionButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageButton.() -> Unit = {}
    ): ImageButton = ctx.styledView(
        newViewRef = ::AppCompatImageButton,
        styleAttr = R.attr.Widget_AppCompat_ActionButton,
        id = id,
        theme = theme,
        initView = initView
    )
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ButtonAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button = ctx.styledView(
        newViewRef = ::AppCompatButton,
        styleAttr = R.attr.Widget_AppCompat_Button,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun colored(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button = ctx.styledView(
        newViewRef = ::AppCompatButton,
        styleAttr = R.attr.Widget_AppCompat_Button_Colored,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun flat(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button = ctx.styledView(
        newViewRef = ::AppCompatButton,
        styleAttr = R.attr.Widget_AppCompat_Button_Borderless,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun borderless(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button = flat(id, theme, initView)

    inline fun flatColored(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button = ctx.styledView(
        newViewRef = ::AppCompatButton,
        styleAttr = R.attr.Widget_AppCompat_Button_Borderless_Colored,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun borderlessColored(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button = flatColored(id, theme, initView)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class TextViewAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun spinnerItem(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextView.() -> Unit = {}
    ): TextView = ctx.styledView(
        newViewRef = ::AppCompatTextView,
        styleAttr = R.attr.Widget_AppCompat_TextView_SpinnerItem,
        id = id,
        theme = theme,
        initView = initView
    )
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class SeekBarAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
    ): SeekBar = ctx.styledView(
        newViewRef = ::AppCompatSeekBar,
        styleAttr = R.attr.Widget_AppCompat_SeekBar,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun discrete(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
    ): SeekBar = ctx.styledView(
        newViewRef = ::AppCompatSeekBar,
        styleAttr = R.attr.Widget_AppCompat_SeekBar_Discrete,
        id = id,
        theme = theme,
        initView = initView
    )
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class SpinnerAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
    ): Spinner = ctx.styledView(
        newViewRef = ::AppCompatSpinner,
        styleAttr = R.attr.Widget_AppCompat_Spinner,
        id = id,
        theme = theme,
        initView = initView
    )

    inline fun underlined(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
    ): Spinner = ctx.styledView(
        newViewRef = ::AppCompatSpinner,
        styleAttr = R.attr.Widget_AppCompat_Spinner_Underlined,
        id = id,
        theme = theme,
        initView = initView
    )
}
