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
