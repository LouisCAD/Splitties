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
package splitties.viewdsl.design

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.design.widget.FloatingActionButton
import android.view.View
import splitties.viewdsl.core.NO_THEME
import splitties.viewdsl.core.Style
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.v

// FloatingActionButton

inline fun Context.floatingActionButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<FloatingActionButton>? = null,
        initView: FloatingActionButton.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.floatingActionButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<FloatingActionButton>? = null,
        initView: FloatingActionButton.() -> Unit = {}
) = context.floatingActionButton(id, theme, style, initView)

inline fun Ui.floatingActionButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<FloatingActionButton>? = null,
        initView: FloatingActionButton.() -> Unit = {}
) = ctx.floatingActionButton(id, theme, style, initView)
