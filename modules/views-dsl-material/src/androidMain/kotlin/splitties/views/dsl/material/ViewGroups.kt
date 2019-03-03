/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.material

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.navigation.NavigationView
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.view
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// AppBarLayout

inline fun Context.appBarLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: AppBarLayout.() -> Unit = {}
): AppBarLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.appBarLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: AppBarLayout.() -> Unit = {}
): AppBarLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.appBarLayout(id, theme, initView)
}

inline fun Ui.appBarLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: AppBarLayout.() -> Unit = {}
): AppBarLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.appBarLayout(id, theme, initView)
}

// CollapsingToolbarLayout

inline fun Context.collapsingToolbarLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CollapsingToolbarLayout.() -> Unit = {}
): CollapsingToolbarLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.collapsingToolbarLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CollapsingToolbarLayout.() -> Unit = {}
): CollapsingToolbarLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.collapsingToolbarLayout(id, theme, initView)
}

inline fun Ui.collapsingToolbarLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CollapsingToolbarLayout.() -> Unit = {}
): CollapsingToolbarLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.collapsingToolbarLayout(id, theme, initView)
}

// NavigationView

inline fun Context.navigationView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: NavigationView.() -> Unit = {}
): NavigationView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.navigationView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: NavigationView.() -> Unit = {}
): NavigationView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.navigationView(id, theme, initView)
}

inline fun Ui.navigationView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: NavigationView.() -> Unit = {}
): NavigationView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.navigationView(id, theme, initView)
}
