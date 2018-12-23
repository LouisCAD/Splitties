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
package splitties.views.dsl.core.styles

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import splitties.views.dsl.core.NO_THEME

inline class AndroidStyles(@PublishedApi internal val ctx: Context) {
    inline val progressBar get() = ProgressBarAndroidStyles(ctx)
    inline val ratingBar get() = RatingBarAndroidStyles(ctx)
    inline val button get() = ButtonAndroidStyles(ctx)
}

//@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
//inline TODO: Make back inline when https://youtrack.jetbrains.com/issue/KT-28542 is fixed.
class ProgressBarAndroidStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun small(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ) = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleSmall)(ctx, id, theme, initView)

    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ) = XmlStyle<ProgressBar>(android.R.attr.progressBarStyle)(ctx, id, theme, initView)

    inline fun large(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ) = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleLarge)(ctx, id, theme, initView)

    inline fun horizontal(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ) = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleHorizontal)(ctx, id, theme, initView)
}

//@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
//inline TODO: Make back inline when https://youtrack.jetbrains.com/issue/KT-28542 is fixed.
class RatingBarAndroidStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun small(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
    ) = XmlStyle<RatingBar>(android.R.attr.ratingBarStyleSmall)(ctx, id, theme, initView)

    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
    ) = XmlStyle<RatingBar>(android.R.attr.ratingBarStyle)(ctx, id, theme, initView)

    inline fun indicator(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
    ) = XmlStyle<RatingBar>(android.R.attr.ratingBarStyleIndicator)(ctx, id, theme, initView)
}

//@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
//inline TODO: Make back inline when https://youtrack.jetbrains.com/issue/KT-28542 is fixed.
class ButtonAndroidStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    /** Small Button style. */
    inline fun small(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(android.R.attr.buttonStyleSmall)(ctx, id, theme, initView)

    /** Normal Button style. */
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(android.R.attr.buttonStyle)(ctx, id, theme, initView)

    /** Style for buttons without an explicit border, often used in groups. */
    inline fun borderless(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(android.R.attr.borderlessButtonStyle)(ctx, id, theme, initView)

    /** Button style to inset into an EditText. */
    inline fun inset(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ) = XmlStyle<Button>(android.R.attr.buttonStyleInset)(ctx, id, theme, initView)
}
