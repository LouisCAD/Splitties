/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.idepreview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import splitties.dimensions.dip
import splitties.exceptions.illegalArg
import splitties.exceptions.unsupported
import splitties.init.injectAsAppCtx
import splitties.resources.str
import splitties.resources.strArray
import splitties.resources.styledColor
import splitties.views.backgroundColor
import splitties.views.dsl.core.*
import splitties.views.gravityCenterVertical
import splitties.views.padding
import splitties.views.setCompoundDrawables
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import splitties.views.dsl.core.R

/**
 * This class is dedicated to previewing `Ui` subclasses in the IDE.
 *
 * You can enable the preview with code or a dedicated xml file.
 *
 * Here's an example in Kotlin:
 *
 * ```kotlin
 * //region IDE preview
 * @Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
 * private class MainUiImplPreview(
 *     context: Context,
 *     attrs: AttributeSet? = null,
 *     defStyleAttr: Int = 0
 * ) : UiPreView(
 *     context = context.withTheme(R.style.AppTheme),
 *     attrs = attrs,
 *     defStyleAttr = defStyleAttr,
 *     createUi = { MainUiImpl(it) }
 * )
 * //endregion
 * ```
 *
 * And here is an example xml layout file that would preview a `MainUi` class in the `main` package:
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
 *
 * Note that only the Kotlin version is safe from refactorings (such as renames, package moving…).
 *
 * If you use the xml approach, it's recommended to add it to your debug sources straightaway.
 * For the Kotlin approach, R8 or proguard will see the class is unused and will strip it so long as you
 * have `minifyEnabled = true`.
 *
 * See the sample for complete examples.
 */
open class UiPreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    createUi: ((Context) -> Ui)? = null
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        backgroundColor = styledColor(android.R.attr.colorBackground)
        require(isInEditMode) { "Only intended for use in IDE!" }
        this.context.injectAsAppCtx()
        try {
            if (createUi == null) {
                init(this.context, attrs, defStyleAttr)
            } else {
                add(createUi(this.context).root, lParams(matchParent, matchParent))
            }
        } catch (t: IllegalArgumentException) {
            backgroundColor = Color.WHITE
            addView(textView {
                text = t.message ?: t.toString()
                setCompoundDrawables(top = R.drawable.ic_warning_red_96dp)
                gravity = gravityCenterVertical
                setTextColor(Color.BLUE)
                padding = dip(16)
                textSize = 24f
            }, lParams(matchParent, matchParent))
        }
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val uiClass: Class<out Ui> = withStyledAttributes(
            attrs = attrs,
            attrsRes = R.styleable.UiPreView,
            defStyleAttr = defStyleAttr,
            defStyleRes = 0
        ) { ta ->
            ta.getString(R.styleable.UiPreView_splitties_class_fully_qualified_name)?.let {
                try {
                    @Suppress("UNCHECKED_CAST")
                    Class.forName(it) as Class<out Ui>
                } catch (e: ClassNotFoundException) {
                    illegalArg("Did not find the specified class: $it")
                }
            } ?: ta.getString(R.styleable.UiPreView_splitties_class_package_name_relative)?.let {
                val packageName = context.packageName.removeSuffix(
                    suffix = str(R.string.splitties_views_dsl_ide_preview_package_name_suffix)
                )
                try {
                    @Suppress("UNCHECKED_CAST")
                    Class.forName("$packageName.$it") as Class<out Ui>
                } catch (e: ClassNotFoundException) {
                    val otherPackages =
                        context.strArray(R.array.splitties_ui_preview_base_package_names)
                    otherPackages.fold<String, Class<out Ui>?>(null) { foundOrNull, packageNameHierarchy ->
                        foundOrNull ?: try {
                            @Suppress("UNCHECKED_CAST")
                            Class.forName("$packageNameHierarchy.$it") as Class<out Ui>
                        } catch (e: ClassNotFoundException) {
                            null
                        }
                    } ?: illegalArg(
                        "Package-name relative class \"$it\" not found!\nDid you make a typo?\n\n" +
                            "Searched in the following root packages:\n" +
                            "- $packageName\n" +
                            otherPackages.joinToString(separator = "\n", prefix = "- ")
                    )
                }
            } ?: illegalArg("No class name attribute provided")
        }
        require(!uiClass.isInterface) { "$uiClass is not instantiable because it's an interface!" }
        require(Ui::class.java.isAssignableFrom(uiClass)) { "$uiClass is not a subclass of Ui!" }
        val ui = try {
            val uiConstructor: Constructor<out Ui> = uiClass.getConstructor(Context::class.java)
            uiConstructor.newInstance(context)
        } catch (e: NoSuchMethodException) {
            val uiConstructor = uiClass.constructors.firstOrNull {
                it.parameterTypes.withIndex().all { (i, parameterType) ->
                    (i == 0 && parameterType == Context::class.java) || parameterType.isInterface
                }
            } ?: illegalArg(
                "No suitable constructor found. Need one with Context as " +
                    "first parameter, and only interface types for other parameters, if any."
            )

            @Suppress("UNUSED_ANONYMOUS_PARAMETER")
            val parameters = mutableListOf<Any>(context).also { params ->
                uiConstructor.parameterTypes.forEachIndexed { index, parameterType ->
                    if (index != 0) params += when (parameterType) {
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
            }.toTypedArray()
            uiConstructor.newInstance(*parameters) as Ui
        }
        addView(ui.root, lParams(matchParent, matchParent))
    }
}

@OptIn(ExperimentalContracts::class)
private inline fun <R> View.withStyledAttributes(
    attrs: AttributeSet?,
    attrsRes: IntArray,
    defStyleAttr: Int,
    defStyleRes: Int = 0,
    func: (styledAttrs: TypedArray) -> R
): R {
    contract { callsInPlace(func, InvocationKind.EXACTLY_ONCE) }
    val styledAttrs = context.obtainStyledAttributes(attrs, attrsRes, defStyleAttr, defStyleRes)
    return func(styledAttrs).also { styledAttrs.recycle() }
}
