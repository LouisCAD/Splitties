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
package splitties.viewdsl.core.experimental

import android.content.Context
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import splitties.collections.forEachReversedByIndex
import splitties.exceptions.illegalArg

class ViewFactory {
    fun add(factory: (Class<out View>, Context, Style<out View>?) -> View?) {
        _list.add(factory)
    }

    operator fun <V : View> invoke(clazz: Class<out V>, context: Context, style: Style<out V>?): V {
        _list.forEachReversedByIndex { factory ->
            factory(clazz, context, style)?.let { view ->
                check(clazz.isInstance(view)) {
                    "Expected type $clazz but got ${view.javaClass}! Faulty factory: $factory"
                }
                @Suppress("UNCHECKED_CAST")
                return view as V
            }
        }
        illegalArg("No factory found for this type: $clazz")
    }

    private val _list = mutableListOf({ clazz: Class<out View>, context: Context, _: Style<out View>? ->
        when (clazz) {
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
            else -> null
        }
    })
}
