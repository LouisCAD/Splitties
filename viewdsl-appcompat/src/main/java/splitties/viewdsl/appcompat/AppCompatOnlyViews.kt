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
package splitties.viewdsl.appcompat

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.v7.widget.Toolbar
import android.view.View
import splitties.viewdsl.core.NO_THEME
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.experimental.styles.Style
import splitties.viewdsl.core.v

// Toolbar

inline fun Context.toolbar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Toolbar>? = null,
        initView: Toolbar.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.toolbar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Toolbar>? = null,
        initView: Toolbar.() -> Unit = {}
) = context.toolbar(id, theme, style, initView)

inline fun Ui.toolbar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Toolbar>? = null,
        initView: Toolbar.() -> Unit = {}
) = ctx.toolbar(id, theme, style, initView)
