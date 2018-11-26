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
package splitties.views.dsl.design

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.view
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// CoordinatorLayout

inline fun Context.coordinatorLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CoordinatorLayout.() -> Unit = {}
): CoordinatorLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::CoordinatorLayout, id, theme, initView)
}

inline fun View.coordinatorLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CoordinatorLayout.() -> Unit = {}
): CoordinatorLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.coordinatorLayout(id, theme, initView)
}

inline fun Ui.coordinatorLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CoordinatorLayout.() -> Unit = {}
): CoordinatorLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.coordinatorLayout(id, theme, initView)
}


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
