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
package splitties.viewdsl.core

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
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

// TextView

inline fun Context.textView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextView.() -> Unit = {}
): TextView = v(id, theme, initView)

inline fun View.textView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextView.() -> Unit = {}
) = context.textView(id, theme, initView)

inline fun Ui.textView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: TextView.() -> Unit = {}
) = ctx.textView(id, theme, initView)

// Button

inline fun Context.button(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
): Button = v(id, theme, initView)

inline fun View.button(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
) = context.button(id, theme, initView)

inline fun Ui.button(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Button.() -> Unit = {}
) = ctx.button(id, theme, initView)

// ImageView

inline fun Context.imageView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageView.() -> Unit = {}
): ImageView = v(id, theme, initView)

inline fun View.imageView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageView.() -> Unit = {}
) = context.imageView(id, theme, initView)

inline fun Ui.imageView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageView.() -> Unit = {}
) = ctx.imageView(id, theme, initView)

// EditText

inline fun Context.editText(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: EditText.() -> Unit = {}
): EditText = v(id, theme, initView)

inline fun View.editText(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: EditText.() -> Unit = {}
) = context.editText(id, theme, initView)

inline fun Ui.editText(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: EditText.() -> Unit = {}
) = ctx.editText(id, theme, initView)

// Spinner

inline fun Context.spinner(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
): Spinner = v(id, theme, initView)

inline fun View.spinner(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
) = context.spinner(id, theme, initView)

inline fun Ui.spinner(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Spinner.() -> Unit = {}
) = ctx.spinner(id, theme, initView)

// ImageButton

inline fun Context.imageButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageButton.() -> Unit = {}
): ImageButton = v(id, theme, initView)

inline fun View.imageButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageButton.() -> Unit = {}
) = context.imageButton(id, theme, initView)

inline fun Ui.imageButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: ImageButton.() -> Unit = {}
) = ctx.imageButton(id, theme, initView)

// CheckBox

inline fun Context.checkBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: CheckBox.() -> Unit = {}
): CheckBox = v(id, theme, initView)

inline fun View.checkBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: CheckBox.() -> Unit = {}
) = context.checkBox(id, theme, initView)

inline fun Ui.checkBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: CheckBox.() -> Unit = {}
) = ctx.checkBox(id, theme, initView)

// RadioButton

inline fun Context.radioButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RadioButton.() -> Unit = {}
): RadioButton = v(id, theme, initView)

inline fun View.radioButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RadioButton.() -> Unit = {}
) = context.radioButton(id, theme, initView)

inline fun Ui.radioButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RadioButton.() -> Unit = {}
) = ctx.radioButton(id, theme, initView)

// CheckedTextView

inline fun Context.checkedTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: CheckedTextView.() -> Unit = {}
): CheckedTextView = v(id, theme, initView)

inline fun View.checkedTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: CheckedTextView.() -> Unit = {}
) = context.checkedTextView(id, theme, initView)

inline fun Ui.checkedTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: CheckedTextView.() -> Unit = {}
) = ctx.checkedTextView(id, theme, initView)

// AutoCompleteTextView

inline fun Context.autoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: AutoCompleteTextView.() -> Unit = {}
): AutoCompleteTextView = v(id, theme, initView)

inline fun View.autoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: AutoCompleteTextView.() -> Unit = {}
) = context.autoCompleteTextView(id, theme, initView)

inline fun Ui.autoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: AutoCompleteTextView.() -> Unit = {}
) = ctx.autoCompleteTextView(id, theme, initView)

// MultiAutoCompleteTextView

inline fun Context.multiAutoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView = v(id, theme, initView)

inline fun View.multiAutoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MultiAutoCompleteTextView.() -> Unit = {}
) = context.multiAutoCompleteTextView(id, theme, initView)

inline fun Ui.multiAutoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: MultiAutoCompleteTextView.() -> Unit = {}
) = ctx.multiAutoCompleteTextView(id, theme, initView)

// RatingBar

inline fun Context.ratingBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
): RatingBar = v(id, theme, initView)

inline fun View.ratingBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
) = context.ratingBar(id, theme, initView)

inline fun Ui.ratingBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RatingBar.() -> Unit = {}
) = ctx.ratingBar(id, theme, initView)

// SeekBar

inline fun Context.seekBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
): SeekBar = v(id, theme, initView)

inline fun View.seekBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
) = context.seekBar(id, theme, initView)

inline fun Ui.seekBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: SeekBar.() -> Unit = {}
) = ctx.seekBar(id, theme, initView)
