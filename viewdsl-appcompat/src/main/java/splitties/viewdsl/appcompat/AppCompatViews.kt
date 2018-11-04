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
@file:Suppress("NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith")

package splitties.viewdsl.appcompat

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

/** Matches [android.support.v7.app.AppCompatViewInflater.createView] content. */
private const val FILE_INFO = 0

private const val deprecationMsgExtensionFunction = "extension function available on " +
        "Context, View and Ui."

@Deprecated("Replace v(::textView) with textView() $deprecationMsgExtensionFunction")
inline fun textView(ctx: Context): TextView = AppCompatTextView(ctx)

@Deprecated("Replace v(::imageView) with imageView() $deprecationMsgExtensionFunction")
inline fun imageView(ctx: Context): ImageView = AppCompatImageView(ctx)

@Deprecated("Replace v(::button) with button() $deprecationMsgExtensionFunction")
inline fun button(ctx: Context): Button = AppCompatButton(ctx)

@Deprecated("Replace v(::editText) with editText() $deprecationMsgExtensionFunction")
inline fun editText(ctx: Context): EditText = AppCompatEditText(ctx)

@Deprecated("Replace v(::spinner) with spinner() $deprecationMsgExtensionFunction")
inline fun spinner(ctx: Context): Spinner = AppCompatSpinner(ctx)

@Deprecated("Replace v(::imageButton) with imageButton() $deprecationMsgExtensionFunction")
inline fun imageButton(ctx: Context): ImageButton = AppCompatImageButton(ctx)

@Deprecated("Replace v(::checkBox) with checkBox() $deprecationMsgExtensionFunction")
inline fun checkBox(ctx: Context): CheckBox = AppCompatCheckBox(ctx)

@Deprecated("Replace v(::radioButton) with radioButton() $deprecationMsgExtensionFunction")
inline fun radioButton(ctx: Context): RadioButton = AppCompatRadioButton(ctx)

@Deprecated("Replace v(::checkedTextView) with checkedTextView() $deprecationMsgExtensionFunction")
inline fun checkedTextView(ctx: Context): CheckedTextView = AppCompatCheckedTextView(ctx)

@Deprecated("Replace v(::autoCompleteTextView) with autoCompleteTextView() $deprecationMsgExtensionFunction")
inline fun autoCompleteTextView(ctx: Context): AutoCompleteTextView = AppCompatAutoCompleteTextView(ctx)

@Deprecated("Replace v(::multiAutoCompleteTextView) with multiAutoCompleteTextView() $deprecationMsgExtensionFunction")
inline fun multiAutoCompleteTextView(ctx: Context): MultiAutoCompleteTextView = AppCompatMultiAutoCompleteTextView(ctx)

@Deprecated("Replace v(::ratingBar) with ratingBar() $deprecationMsgExtensionFunction")
inline fun ratingBar(ctx: Context): RatingBar = AppCompatRatingBar(ctx)

@Deprecated("Replace v(::seekBar) with seekBar() $deprecationMsgExtensionFunction")
inline fun seekBar(ctx: Context): SeekBar = AppCompatSeekBar(ctx)
