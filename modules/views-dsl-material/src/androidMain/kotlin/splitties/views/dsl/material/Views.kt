/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.material

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.view
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// FloatingActionButton

inline fun Context.floatingActionButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FloatingActionButton.() -> Unit = {}
): FloatingActionButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.floatingActionButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FloatingActionButton.() -> Unit = {}
): FloatingActionButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.floatingActionButton(id, theme, initView)
}

inline fun Ui.floatingActionButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FloatingActionButton.() -> Unit = {}
): FloatingActionButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.floatingActionButton(id, theme, initView)
}

// MaterialCardView

inline fun Context.materialCardView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: MaterialCardView.() -> Unit = {}
): MaterialCardView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.materialCardView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: MaterialCardView.() -> Unit = {}
): MaterialCardView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.materialCardView(id, theme, initView)
}

inline fun Ui.materialCardView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: MaterialCardView.() -> Unit = {}
): MaterialCardView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.materialCardView(id, theme, initView)
}
