/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
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
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@JvmInline
value class AndroidStyles(@PublishedApi internal val ctx: Context) {
    inline val progressBar get() = ProgressBarAndroidStyles(ctx)
    inline val ratingBar get() = RatingBarAndroidStyles(ctx)
    inline val button get() = ButtonAndroidStyles(ctx)
}

@JvmInline
value class ProgressBarAndroidStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun small(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ): ProgressBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<ProgressBar>(android.R.attr.progressBarStyleSmall)(ctx, id, theme, initView)
    }

    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ): ProgressBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<ProgressBar>(android.R.attr.progressBarStyle)(ctx, id, theme, initView)
    }

    inline fun large(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ): ProgressBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<ProgressBar>(android.R.attr.progressBarStyleLarge)(ctx, id, theme, initView)
    }

    inline fun horizontal(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ProgressBar.() -> Unit = {}
    ): ProgressBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<ProgressBar>(android.R.attr.progressBarStyleHorizontal)(ctx, id, theme, initView)
    }
}

@JvmInline
value class RatingBarAndroidStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun small(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
    ): RatingBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<RatingBar>(android.R.attr.ratingBarStyleSmall)(ctx, id, theme, initView)
    }

    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
    ): RatingBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<RatingBar>(android.R.attr.ratingBarStyle)(ctx, id, theme, initView)
    }

    inline fun indicator(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
    ): RatingBar {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<RatingBar>(android.R.attr.ratingBarStyleIndicator)(ctx, id, theme, initView)
    }
}

@JvmInline
value class ButtonAndroidStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    /** Small Button style. */
    inline fun small(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<Button>(android.R.attr.buttonStyleSmall)(ctx, id, theme, initView)
    }

    /** Normal Button style. */
    inline fun default(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<Button>(android.R.attr.buttonStyle)(ctx, id, theme, initView)
    }

    /** Style for buttons without an explicit border, often used in groups. */
    inline fun borderless(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<Button>(android.R.attr.borderlessButtonStyle)(ctx, id, theme, initView)
    }

    /** Button style to inset into an EditText. */
    inline fun inset(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
    ): Button {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return XmlStyle<Button>(android.R.attr.buttonStyleInset)(ctx, id, theme, initView)
    }
}
