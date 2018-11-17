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
package splitties.views.dsl.core

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
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
