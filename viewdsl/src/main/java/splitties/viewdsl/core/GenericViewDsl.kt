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
package splitties.viewdsl.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.view.View
import splitties.viewdsl.core.experimental.styles.Style

private const val VIEW_FACTORY = "splitties:viewdsl:viewfactory"
val Context.viewFactory: ViewFactory
    @SuppressLint("WrongConstant")
    get() = getSystemService(VIEW_FACTORY) as ViewFactory? ?: ViewFactory.appInstance

fun Context.withViewFactory(viewFactory: ViewFactory) = object : ContextWrapper(this) {
    override fun getSystemService(name: String): Any? = when(name) {
        VIEW_FACTORY -> viewFactory
        else -> super.getSystemService(name)
    }
}

inline fun <reified V : View> Context.v(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<V>? = null,
        initView: V.() -> Unit = {}
): V = viewFactory(V::class.java, wrapCtxIfNeeded(theme), style).also { it.id = id }.apply(initView)

inline fun <reified V : View> View.v(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<V>? = null,
        initView: V.() -> Unit = {}
) = context.v(id, theme, style, initView)

inline fun <reified V : View> Ui.v(
        @IdRes id: Int = android.view.View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<V>? = null,
        initView: V.() -> Unit = {}
) = ctx.v(id, theme, style, initView)
