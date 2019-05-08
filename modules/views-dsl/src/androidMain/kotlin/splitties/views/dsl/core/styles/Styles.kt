/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.core.styles

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.getThemeAttrStyledView
import splitties.views.dsl.core.viewFactory
import splitties.views.dsl.core.wrapCtxIfNeeded

typealias NewViewWithStyleAttrRef<V> = (Context, AttributeSet?, Int) -> V

inline operator fun <reified V : View> XmlStyle<V>.invoke(
    ctx: Context,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V = ctx.viewFactory.getThemeAttrStyledView<V>(ctx.wrapCtxIfNeeded(theme), null, styleAttr).also {
    it.id = id
}.apply(initView)

inline fun <V : View> Context.styledView(
    newViewRef: NewViewWithStyleAttrRef<V>,
    @AttrRes styleAttr: Int,
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: V.() -> Unit = {}
): V = newViewRef(this.wrapCtxIfNeeded(theme), null, styleAttr).also {
    it.id = id
}.apply(initView)
