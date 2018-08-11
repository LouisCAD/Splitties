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
import splitties.viewdsl.core.NO_THEME
import splitties.viewdsl.core.Ui

// TextView

inline fun Context.textView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<TextView>? = null,
        initView: TextView.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.textView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<TextView>? = null,
        initView: TextView.() -> Unit = {}
) = context.textView(id, theme, style, initView)

inline fun Ui.textView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<TextView>? = null,
        initView: TextView.() -> Unit = {}
) = ctx.textView(id, theme, style, initView)

// Button

inline fun Context.button(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Button>? = null,
        initView: Button.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.button(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Button>? = null,
        initView: Button.() -> Unit = {}
) = context.button(id, theme, style, initView)

inline fun Ui.button(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Button>? = null,
        initView: Button.() -> Unit = {}
) = ctx.button(id, theme, style, initView)

// ImageView

inline fun Context.imageView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<ImageView>? = null,
        initView: ImageView.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.imageView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<ImageView>? = null,
        initView: ImageView.() -> Unit = {}
) = context.imageView(id, theme, style, initView)

inline fun Ui.imageView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<ImageView>? = null,
        initView: ImageView.() -> Unit = {}
) = ctx.imageView(id, theme, style, initView)

// EditText

inline fun Context.editText(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<EditText>? = null,
        initView: EditText.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.editText(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<EditText>? = null,
        initView: EditText.() -> Unit = {}
) = context.editText(id, theme, style, initView)

inline fun Ui.editText(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<EditText>? = null,
        initView: EditText.() -> Unit = {}
) = ctx.editText(id, theme, style, initView)

// Spinner

inline fun Context.spinner(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Spinner>? = null,
        initView: Spinner.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.spinner(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Spinner>? = null,
        initView: Spinner.() -> Unit = {}
) = context.spinner(id, theme, style, initView)

inline fun Ui.spinner(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<Spinner>? = null,
        initView: Spinner.() -> Unit = {}
) = ctx.spinner(id, theme, style, initView)

// ImageButton

inline fun Context.imageButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<ImageButton>? = null,
        initView: ImageButton.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.imageButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<ImageButton>? = null,
        initView: ImageButton.() -> Unit = {}
) = context.imageButton(id, theme, style, initView)

inline fun Ui.imageButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<ImageButton>? = null,
        initView: ImageButton.() -> Unit = {}
) = ctx.imageButton(id, theme, style, initView)

// CheckBox

inline fun Context.checkBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<CheckBox>? = null,
        initView: CheckBox.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.checkBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<CheckBox>? = null,
        initView: CheckBox.() -> Unit = {}
) = context.checkBox(id, theme, style, initView)

inline fun Ui.checkBox(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<CheckBox>? = null,
        initView: CheckBox.() -> Unit = {}
) = ctx.checkBox(id, theme, style, initView)

// RadioButton

inline fun Context.radioButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<RadioButton>? = null,
        initView: RadioButton.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.radioButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<RadioButton>? = null,
        initView: RadioButton.() -> Unit = {}
) = context.radioButton(id, theme, style, initView)

inline fun Ui.radioButton(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<RadioButton>? = null,
        initView: RadioButton.() -> Unit = {}
) = ctx.radioButton(id, theme, style, initView)

// CheckedTextView

inline fun Context.checkedTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<CheckedTextView>? = null,
        initView: CheckedTextView.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.checkedTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<CheckedTextView>? = null,
        initView: CheckedTextView.() -> Unit = {}
) = context.checkedTextView(id, theme, style, initView)

inline fun Ui.checkedTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<CheckedTextView>? = null,
        initView: CheckedTextView.() -> Unit = {}
) = ctx.checkedTextView(id, theme, style, initView)

// AutoCompleteTextView

inline fun Context.autoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<AutoCompleteTextView>? = null,
        initView: AutoCompleteTextView.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.autoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<AutoCompleteTextView>? = null,
        initView: AutoCompleteTextView.() -> Unit = {}
) = context.autoCompleteTextView(id, theme, style, initView)

inline fun Ui.autoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<AutoCompleteTextView>? = null,
        initView: AutoCompleteTextView.() -> Unit = {}
) = ctx.autoCompleteTextView(id, theme, style, initView)

// MultiAutoCompleteTextView

inline fun Context.multiAutoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<MultiAutoCompleteTextView>? = null,
        initView: MultiAutoCompleteTextView.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.multiAutoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<MultiAutoCompleteTextView>? = null,
        initView: MultiAutoCompleteTextView.() -> Unit = {}
) = context.multiAutoCompleteTextView(id, theme, style, initView)

inline fun Ui.multiAutoCompleteTextView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<MultiAutoCompleteTextView>? = null,
        initView: MultiAutoCompleteTextView.() -> Unit = {}
) = ctx.multiAutoCompleteTextView(id, theme, style, initView)

// RatingBar

inline fun Context.ratingBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<RatingBar>? = null,
        initView: RatingBar.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.ratingBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<RatingBar>? = null,
        initView: RatingBar.() -> Unit = {}
) = context.ratingBar(id, theme, style, initView)

inline fun Ui.ratingBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<RatingBar>? = null,
        initView: RatingBar.() -> Unit = {}
) = ctx.ratingBar(id, theme, style, initView)

// SeekBar

inline fun Context.seekBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<SeekBar>? = null,
        initView: SeekBar.() -> Unit = {}
) = v(id, theme, style, initView)

inline fun View.seekBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<SeekBar>? = null,
        initView: SeekBar.() -> Unit = {}
) = context.seekBar(id, theme, style, initView)

inline fun Ui.seekBar(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        style: Style<SeekBar>? = null,
        initView: SeekBar.() -> Unit = {}
) = ctx.seekBar(id, theme, style, initView)
