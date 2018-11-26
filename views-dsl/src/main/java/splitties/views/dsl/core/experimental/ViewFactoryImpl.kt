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
package splitties.views.dsl.core.experimental

import android.content.Context
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.view.View
import android.widget.*
import splitties.collections.forEachReversedByIndex
import splitties.exceptions.illegalArg
import splitties.views.dsl.core.ViewFactory
import java.lang.reflect.Constructor

typealias ViewInstantiator = (Class<out View>, Context) -> View?
typealias ThemeAttrStyledViewInstantiator = (Class<out View>, Context, Int) -> View?

class ViewFactoryImpl : ViewFactory {
    companion object {
        val appInstance = ViewFactoryImpl()
    }

    fun add(factory: ViewInstantiator) {
        viewInstantiators.add(factory)
    }

    fun addForThemeAttrStyled(factory: ThemeAttrStyledViewInstantiator) {
        themeAttrStyledViewInstantiators.add(factory)
    }

    override operator fun <V : View> invoke(clazz: Class<out V>, context: Context): V {
        viewInstantiators.forEachReversedByIndex { factory ->
            @Suppress("UNCHECKED_CAST")
            factory(clazz, context)?.let { view ->
                check(clazz.isInstance(view)) {
                    "Expected type $clazz but got ${view.javaClass}! Faulty factory: $factory"
                }
                return view as V
            }
        }
        illegalArg("No factory found for this type: $clazz")
    }

    override fun <V : View> getThemeAttributeStyledView(
        clazz: Class<out V>,
        context: Context,
        @Suppress("UNUSED_PARAMETER") attrs: AttributeSet?,
        @AttrRes styleThemeAttribute: Int
    ): V {
        themeAttrStyledViewInstantiators.forEachReversedByIndex { factory ->
            factory(clazz, context, styleThemeAttribute)?.let { view ->
                check(clazz.isInstance(view)) {
                    "Expected type $clazz but got ${view.javaClass}! Faulty factory: $factory"
                }
                @Suppress("UNCHECKED_CAST")
                return view as V
            }
        }
        illegalArg("No factory found for this type: $clazz")
    }

    private val viewInstantiators: MutableList<ViewInstantiator> = mutableListOf(::instantiateView)
    private val themeAttrStyledViewInstantiators: MutableList<ThemeAttrStyledViewInstantiator> =
        mutableListOf(::instantiateThemeAttrStyledView)
}

private inline fun <reified V : View> instantiateView(
    clazz: Class<out V>,
    context: Context
): V? = when (clazz) {
    TextView::class.java -> TextView(context)
    Button::class.java -> Button(context)
    ImageView::class.java -> ImageView(context)
    EditText::class.java -> EditText(context)
    Spinner::class.java -> Spinner(context)
    ImageButton::class.java -> ImageButton(context)
    CheckBox::class.java -> CheckBox(context)
    RadioButton::class.java -> RadioButton(context)
    CheckedTextView::class.java -> CheckedTextView(context)
    AutoCompleteTextView::class.java -> AutoCompleteTextView(context)
    MultiAutoCompleteTextView::class.java -> MultiAutoCompleteTextView(context)
    RatingBar::class.java -> RatingBar(context)
    SeekBar::class.java -> SeekBar(context)
    else -> clazz.viewConstructor().newInstance(context)
} as V?

private inline fun <reified V : View> instantiateThemeAttrStyledView(
    clazz: Class<out V>,
    context: Context,
    @AttrRes styleThemeAttribute: Int
): V? = when (clazz) {
    TextView::class.java -> TextView(context, null, styleThemeAttribute)
    Button::class.java -> Button(context, null, styleThemeAttribute)
    ImageView::class.java -> ImageView(context, null, styleThemeAttribute)
    EditText::class.java -> EditText(context, null, styleThemeAttribute)
    Spinner::class.java -> Spinner(context, null, styleThemeAttribute)
    ImageButton::class.java -> ImageButton(context, null, styleThemeAttribute)
    CheckBox::class.java -> CheckBox(context, null, styleThemeAttribute)
    RadioButton::class.java -> RadioButton(context, null, styleThemeAttribute)
    CheckedTextView::class.java -> CheckedTextView(context, null, styleThemeAttribute)
    AutoCompleteTextView::class.java -> AutoCompleteTextView(context, null, styleThemeAttribute)
    MultiAutoCompleteTextView::class.java -> {
        MultiAutoCompleteTextView(context, null, styleThemeAttribute)
    }
    RatingBar::class.java -> RatingBar(context, null, styleThemeAttribute)
    SeekBar::class.java -> SeekBar(context, null, styleThemeAttribute)
    else -> clazz.themeAttrStyledViewConstructor().newInstance(context, null, styleThemeAttribute)
} as V?

@Suppress("UNCHECKED_CAST")
private fun <V : View> Class<V>.viewConstructor(): Constructor<V> {
    return cachedViewConstructors[this] as Constructor<V>?
        ?: getConstructor(Context::class.java).also { cachedViewConstructors[this] = it }
}

@Suppress("UNCHECKED_CAST")
private fun <V : View> Class<out V>.themeAttrStyledViewConstructor(): Constructor<out V> {
    return cachedThemeAttrStyledViewConstructors[this] as Constructor<V>?
        ?: getConstructor(Context::class.java, AttributeSet::class.java, Int::class.java).also {
            cachedThemeAttrStyledViewConstructors[this] = it
        }
}

private val cachedViewConstructors by lazy(LazyThreadSafetyMode.PUBLICATION) {
    mutableMapOf<Class<out View>, Constructor<out View>>()
}
private val cachedThemeAttrStyledViewConstructors by lazy(LazyThreadSafetyMode.PUBLICATION) {
    mutableMapOf<Class<out View>, Constructor<out View>>()
}

