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
package splitties.views.dsl.appcompat

import android.content.Context
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.styles.XmlStyle
import splitties.views.dsl.core.styles.invoke

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
    ) = XmlStyle<ImageButton>(R.attr.Widget_AppCompat_ActionButton)(ctx, id, theme, initView)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ButtonAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(R.attr.Widget_AppCompat_Button)(ctx, id, theme, initView)

    inline fun colored(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Colored)(ctx, id, theme, initView)

    inline fun flat(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Borderless)(ctx, id, theme, initView)

    inline fun borderless(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = flat(id, theme, initView)

    inline fun flatColored(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(
        R.attr.Widget_AppCompat_Button_Borderless_Colored
    )(ctx, id, theme, initView)

    inline fun borderlessColored(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = flatColored(id, theme, initView)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class TextViewAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun spinnerItem(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextView.() -> Unit = {}
    ) = XmlStyle<TextView>(R.attr.Widget_AppCompat_TextView_SpinnerItem)(ctx, id, theme, initView)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class SeekBarAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
    ) = XmlStyle<SeekBar>(R.attr.Widget_AppCompat_SeekBar)(ctx, id, theme, initView)
    inline fun discrete(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
    ) = XmlStyle<SeekBar>(R.attr.Widget_AppCompat_SeekBar_Discrete)(ctx, id, theme, initView)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class SpinnerAppCompatStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
    ) = XmlStyle<Spinner>(R.attr.Widget_AppCompat_Spinner)(ctx, id, theme, initView)
    inline fun underlined(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
    ) = XmlStyle<Spinner>(R.attr.Widget_AppCompat_Spinner_Underlined)(ctx, id, theme, initView)
}
