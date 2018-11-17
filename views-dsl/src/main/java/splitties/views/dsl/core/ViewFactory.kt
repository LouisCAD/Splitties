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
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.view.View
import splitties.views.dsl.core.experimental.ViewFactoryImpl

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
