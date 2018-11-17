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
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.View
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
