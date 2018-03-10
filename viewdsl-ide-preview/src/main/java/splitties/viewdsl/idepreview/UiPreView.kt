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

package splitties.viewdsl.idepreview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import splitties.exceptions.illegalArg
import splitties.exceptions.unsupported
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.lParams
import splitties.viewdsl.core.matchParent
import java.lang.reflect.Constructor
import java.lang.reflect.Proxy

class UiPreView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        require(isInEditMode) { "Only intended for use in IDE!" }
        val className = withStyledAttributes(attrs, R.styleable.UiPreView, defStyleAttr, 0) { ta ->
            ta.getString(R.styleable.UiPreView_class_fully_qualified_name)
                    ?: ta.getString(R.styleable.UiPreView_class_package_name_relative)?.let {
                        val debugBuildTypeSuffix = ".debug"
                        val packageName = context.packageName.removeSuffix(debugBuildTypeSuffix)
                        "$packageName.$it"
                    }
                    ?: illegalArg("No class name attribute provided")
        }
        @Suppress("UNCHECKED_CAST")
        val uiClass = Class.forName(className) as Class<out Ui>
        require(Ui::class.java.isAssignableFrom(uiClass)) { "$uiClass is not a subclass of Ui!" }
        require(!uiClass.isInterface) { "$uiClass is not instantiable because it's an interface!" }
        val ui = try {
            val uiConstructor: Constructor<out Ui> = uiClass.getConstructor(Context::class.java)
            uiConstructor.newInstance(context)
        } catch (e: NoSuchMethodException) {
            val uiConstructor = uiClass.constructors.firstOrNull {
                it.parameterTypes.withIndex().all { (i, parameterType) ->
                    (i == 0 && parameterType == Context::class.java) || parameterType.isInterface
                }
            } ?: illegalArg("No suitable constructor found. Need one with Context as" +
                    "first parameter, and only interface types for other parameters, if any.")
            @Suppress("UNUSED_ANONYMOUS_PARAMETER")
            val parameters = mutableListOf<Any>(context).also { params ->
                uiConstructor.parameterTypes.forEachIndexed { index, parameterType ->
                    if (index != 0) params += Proxy.newProxyInstance(
                            parameterType.classLoader,
                            arrayOf(parameterType)
                    ) { proxy, method, args -> unsupported("Edit mode: stub implementation.") }
                }
            }.toTypedArray()
            uiConstructor.newInstance(*parameters) as Ui
        }
        addView(ui.root, lParams(matchParent, matchParent))
    }

    private inline fun <R> View.withStyledAttributes(
            attrs: AttributeSet?, attrsRes: IntArray,
            defStyleAttr: Int, defStyleRes: Int = 0,
            func: (styledAttrs: TypedArray) -> R): R {
        val styledAttrs = context.obtainStyledAttributes(attrs, attrsRes, defStyleAttr, defStyleRes)
        return func(styledAttrs).also { styledAttrs.recycle() }
    }
}
