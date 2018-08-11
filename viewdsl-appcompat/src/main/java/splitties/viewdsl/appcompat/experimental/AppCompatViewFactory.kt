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
package splitties.viewdsl.appcompat.experimental

import android.content.Context
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatCheckedTextView
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView
import android.support.v7.widget.AppCompatRadioButton
import android.support.v7.widget.AppCompatRatingBar
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.AppCompatTextView
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
import splitties.viewdsl.core.experimental.styles.Style

/** Matches [android.support.v7.app.AppCompatViewInflater.createView] content. */
inline fun <reified V: View> instantiateAppCompatView(
        clazz: Class<out V>,
        context: Context,
        @Suppress("UNUSED_PARAMETER") style: Style<out V>?
): V? = when (clazz) {
    TextView::class.java -> AppCompatTextView(context)
    Button::class.java -> AppCompatButton(context)
    ImageView::class.java -> AppCompatImageView(context)
    EditText::class.java -> AppCompatEditText(context)
    Spinner::class.java -> AppCompatSpinner(context)
    ImageButton::class.java -> AppCompatImageButton(context)
    CheckBox::class.java -> AppCompatCheckBox(context)
    RadioButton::class.java -> AppCompatRadioButton(context)
    CheckedTextView::class.java -> AppCompatCheckedTextView(context)
    AutoCompleteTextView::class.java -> AppCompatAutoCompleteTextView(context)
    MultiAutoCompleteTextView::class.java -> AppCompatMultiAutoCompleteTextView(context)
    RatingBar::class.java -> AppCompatRatingBar(context)
    SeekBar::class.java -> AppCompatSeekBar(context)
    else -> null
} as V?
