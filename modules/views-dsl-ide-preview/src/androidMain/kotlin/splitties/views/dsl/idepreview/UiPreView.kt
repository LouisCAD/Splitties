/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.idepreview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import splitties.exceptions.illegalArg
import splitties.exceptions.unsupported
import splitties.init.injectAsAppCtx
import splitties.resources.str
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.lParams
import splitties.views.dsl.core.matchParent
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * This class is dedicated to previewing `Ui` subclasses in the IDE with an xml file referencing it.
 * Add it to your debug build and use this class in xml layout files in your debug sources. See the
 * sample for complete examples.
 *
 * Here is an example xml layout file that would preview a `MainUi` class in the `main` package:
 *
 * ```xml
 * <splitties.views.dsl.idepreview.UiPreView
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schemas.android.com/apk/res-auto"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     android:theme="@style/AppTheme.NoActionBar"
 *     app:class_package_name_relative="main.MainUi"/>
 * ```
 */
class UiPreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        require(isInEditMode) { "Only intended for use in IDE!" }
        context.injectAsAppCtx()
        val className = withStyledAttributes(attrs, R.styleable.UiPreView, defStyleAttr, 0) { ta ->
            val packageNameSuffix =
                str(R.string.splitties_views_dsl_ide_preview_package_name_suffix)
            ta.getString(R.styleable.UiPreView_splitties_class_fully_qualified_name)
                ?: ta.getString(R.styleable.UiPreView_splitties_class_package_name_relative)?.let {
                    val packageName = context.packageName.removeSuffix(packageNameSuffix)
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
            } ?: illegalArg(
                "No suitable constructor found. Need one with Context as" +
                        "first parameter, and only interface types for other parameters, if any."
            )
            @Suppress("UNUSED_ANONYMOUS_PARAMETER")
            val parameters = mutableListOf<Any>(context).also { params ->
                uiConstructor.parameterTypes.forEachIndexed { index, parameterType ->
                    if (index != 0) {
                        params += when (parameterType) {
                            CoroutineContext::class.java -> EmptyCoroutineContext
                            else -> Proxy.newProxyInstance(
                                parameterType.classLoader,
                                arrayOf(parameterType)
                            ) { proxy: Any?, method: Method, args: Array<out Any>? ->
                                when (method.declaringClass.name) {
                                    "kotlinx.coroutines.CoroutineScope" -> EmptyCoroutineContext
                                    else -> unsupported("Edit mode: stub implementation.")
                                }
                            }
                        }
                    }
                }
            }.toTypedArray()
            uiConstructor.newInstance(*parameters) as Ui
        }
        addView(ui.root, lParams(matchParent, matchParent))
    }

    private inline fun <R> View.withStyledAttributes(
        attrs: AttributeSet?, attrsRes: IntArray,
        defStyleAttr: Int, defStyleRes: Int = 0,
        func: (styledAttrs: TypedArray) -> R
    ): R {
        val styledAttrs = context.obtainStyledAttributes(attrs, attrsRes, defStyleAttr, defStyleRes)
        return func(styledAttrs).also { styledAttrs.recycle() }
    }
}
