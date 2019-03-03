/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.appcompat

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.view
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// Toolbar

inline fun Context.toolbar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Toolbar.() -> Unit = {}
): Toolbar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.toolbar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Toolbar.() -> Unit = {}
): Toolbar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.toolbar(id, theme, initView)
}

inline fun Ui.toolbar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Toolbar.() -> Unit = {}
): Toolbar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.toolbar(id, theme, initView)
}

// SwitchCompat

inline fun Context.switch(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: SwitchCompat.() -> Unit = {}
): SwitchCompat {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::SwitchCompat, id, theme, initView)
}

inline fun View.switch(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: SwitchCompat.() -> Unit = {}
): SwitchCompat {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.switch(id, theme, initView)
}

inline fun Ui.switch(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: SwitchCompat.() -> Unit = {}
): SwitchCompat {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.switch(id, theme, initView)
}
