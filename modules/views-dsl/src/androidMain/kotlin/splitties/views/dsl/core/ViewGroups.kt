/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:JvmName("ViewsGroupsKt")

package splitties.views.dsl.core

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// LinearLayout (vertical)

inline fun Context.verticalLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: LinearLayout.() -> Unit = {}
): LinearLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::LinearLayout, id, theme) {
        orientation = LinearLayout.VERTICAL
        initView()
    }
}

inline fun View.verticalLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: LinearLayout.() -> Unit = {}
): LinearLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.verticalLayout(id, theme, initView)
}

inline fun Ui.verticalLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: LinearLayout.() -> Unit = {}
): LinearLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.verticalLayout(id, theme, initView)
}

// LinearLayout (horizontal)

inline fun Context.horizontalLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: LinearLayout.() -> Unit = {}
): LinearLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::LinearLayout, id, theme, initView)
}

inline fun View.horizontalLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: LinearLayout.() -> Unit = {}
): LinearLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.horizontalLayout(id, theme, initView)
}

inline fun Ui.horizontalLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: LinearLayout.() -> Unit = {}
): LinearLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.horizontalLayout(id, theme, initView)
}

// FrameLayout

inline fun Context.frameLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FrameLayout.() -> Unit = {}
): FrameLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::FrameLayout, id, theme, initView)
}

inline fun View.frameLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FrameLayout.() -> Unit = {}
): FrameLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.frameLayout(id, theme, initView)
}

inline fun Ui.frameLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: FrameLayout.() -> Unit = {}
): FrameLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.frameLayout(id, theme, initView)
}

// RadioGroup

inline fun Context.radioGroup(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RadioGroup.() -> Unit = {}
): RadioGroup {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::RadioGroup, id, theme, initView)
}

inline fun View.radioGroup(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RadioGroup.() -> Unit = {}
): RadioGroup {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.radioGroup(id, theme, initView)
}

inline fun Ui.radioGroup(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RadioGroup.() -> Unit = {}
): RadioGroup {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.radioGroup(id, theme, initView)
}
