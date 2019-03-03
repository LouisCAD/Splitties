/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import splitties.experimental.InternalSplittiesApi
import splitties.views.dsl.core.experimental.ViewFactoryImpl

@InternalSplittiesApi
interface ViewFactory {
    companion object {
        val appInstance: ViewFactory = ViewFactoryImpl.appInstance
    }

    operator fun <V : View> invoke(clazz: Class<out V>, context: Context): V
    fun <V : View> getThemeAttributeStyledView(
        clazz: Class<out V>,
        context: Context,
        @Suppress("UNUSED_PARAMETER") attrs: AttributeSet?,
        @AttrRes styleThemeAttribute: Int
    ): V
}

inline fun <reified V : View> ViewFactory.getThemeAttrStyledView(
    context: Context,
    attrs: AttributeSet?,
    @AttrRes styleThemeAttribute: Int
): V = getThemeAttributeStyledView(V::class.java, context, attrs, styleThemeAttribute)
