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

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import splitties.views.inflate
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** Called so to remind that function references (that are inlined) are recommended for [view]. */
typealias NewViewRef<V> = (Context) -> V

const val NO_THEME = 0

@Suppress("NOTHING_TO_INLINE")
inline fun Context.withTheme(theme: Int) = ContextThemeWrapper(this, theme)

fun Context.wrapCtxIfNeeded(theme: Int): Context {
    return if (theme == NO_THEME) this else withTheme(theme)
}

inline fun <V : View> Context.view(
    createView: NewViewRef<V>,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return createView(wrapCtxIfNeeded(theme)).also { it.id = id }.apply(initView)
}

inline fun <V : View> View.view(
    createView: NewViewRef<V>,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.view(createView, id, theme, initView)
}

inline fun <V : View> Ui.view(
    createView: NewViewRef<V>,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.view(createView, id, theme, initView)
}

private const val VIEW_FACTORY = "splitties:views.dsl:viewfactory"

val Context.viewFactory: ViewFactory
    @SuppressLint("WrongConstant")
    get() = getSystemService(VIEW_FACTORY) as ViewFactory? ?: ViewFactory.appInstance

fun Context.withViewFactory(viewFactory: ViewFactory): Context = object : ContextWrapper(this) {
    override fun getSystemService(name: String): Any? = when (name) {
        VIEW_FACTORY -> viewFactory
        else -> super.getSystemService(name)
    }
}

inline fun <reified V : View> Context.view(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return viewFactory(V::class.java, wrapCtxIfNeeded(theme)).also {
        it.id = id
    }.apply(initView)
}

inline fun <reified V : View> View.view(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.view(id, theme, initView)
}

inline fun <reified V : View> Ui.view(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.view(id, theme, initView)
}

@PublishedApi
internal const val XML_DEFINED_ID = -1

inline fun <reified V : View> Context.inflate(
    @LayoutRes layoutResId: Int,
    @IdRes id: Int = XML_DEFINED_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return wrapCtxIfNeeded(theme).inflate<V>(layoutResId).also { inflatedView ->
        if (id != XML_DEFINED_ID) inflatedView.id = id
    }.apply(initView)
}

inline fun <reified V : View> View.inflate(
    @LayoutRes layoutResId: Int,
    @IdRes id: Int = XML_DEFINED_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.inflate(layoutResId, id, theme, initView)
}

inline fun <reified V : View> Ui.inflate(
    @LayoutRes layoutResId: Int,
    @IdRes id: Int = XML_DEFINED_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.inflate(layoutResId, id, theme, initView)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <V : View> ViewGroup.add(
    view: V,
    lp: ViewGroup.LayoutParams
): V = view.also { addView(it, lp) }
