/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.core

import android.content.Context
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// TextView

inline fun Context.textView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: TextView.() -> Unit = {}
): TextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.textView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: TextView.() -> Unit = {}
): TextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.textView(id, theme, initView)
}

inline fun Ui.textView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: TextView.() -> Unit = {}
): TextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.textView(id, theme, initView)
}

// Button

inline fun Context.button(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Button.() -> Unit = {}
): Button {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.button(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Button.() -> Unit = {}
): Button {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.button(id, theme, initView)
}

inline fun Ui.button(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Button.() -> Unit = {}
): Button {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.button(id, theme, initView)
}

// ImageView

inline fun Context.imageView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: ImageView.() -> Unit = {}
): ImageView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.imageView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: ImageView.() -> Unit = {}
): ImageView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.imageView(id, theme, initView)
}

inline fun Ui.imageView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: ImageView.() -> Unit = {}
): ImageView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.imageView(id, theme, initView)
}

// EditText

inline fun Context.editText(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: EditText.() -> Unit = {}
): EditText {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.editText(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: EditText.() -> Unit = {}
): EditText {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.editText(id, theme, initView)
}

inline fun Ui.editText(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: EditText.() -> Unit = {}
): EditText {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.editText(id, theme, initView)
}

// Spinner

inline fun Context.spinner(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Spinner.() -> Unit = {}
): Spinner {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.spinner(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Spinner.() -> Unit = {}
): Spinner {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.spinner(id, theme, initView)
}

inline fun Ui.spinner(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: Spinner.() -> Unit = {}
): Spinner {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.spinner(id, theme, initView)
}

// ImageButton

inline fun Context.imageButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: ImageButton.() -> Unit = {}
): ImageButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.imageButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: ImageButton.() -> Unit = {}
): ImageButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.imageButton(id, theme, initView)
}

inline fun Ui.imageButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: ImageButton.() -> Unit = {}
): ImageButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.imageButton(id, theme, initView)
}

// CheckBox

inline fun Context.checkBox(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CheckBox.() -> Unit = {}
): CheckBox {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.checkBox(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CheckBox.() -> Unit = {}
): CheckBox {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.checkBox(id, theme, initView)
}

inline fun Ui.checkBox(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CheckBox.() -> Unit = {}
): CheckBox {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.checkBox(id, theme, initView)
}

// RadioButton

inline fun Context.radioButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RadioButton.() -> Unit = {}
): RadioButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.radioButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RadioButton.() -> Unit = {}
): RadioButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.radioButton(id, theme, initView)
}

inline fun Ui.radioButton(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RadioButton.() -> Unit = {}
): RadioButton {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.radioButton(id, theme, initView)
}

// CheckedTextView

inline fun Context.checkedTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CheckedTextView.() -> Unit = {}
): CheckedTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.checkedTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CheckedTextView.() -> Unit = {}
): CheckedTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.checkedTextView(id, theme, initView)
}

inline fun Ui.checkedTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CheckedTextView.() -> Unit = {}
): CheckedTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.checkedTextView(id, theme, initView)
}

// AutoCompleteTextView

inline fun Context.autoCompleteTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: AutoCompleteTextView.() -> Unit = {}
): AutoCompleteTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.autoCompleteTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: AutoCompleteTextView.() -> Unit = {}
): AutoCompleteTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.autoCompleteTextView(id, theme, initView)
}

inline fun Ui.autoCompleteTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: AutoCompleteTextView.() -> Unit = {}
): AutoCompleteTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.autoCompleteTextView(id, theme, initView)
}

// MultiAutoCompleteTextView

inline fun Context.multiAutoCompleteTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.multiAutoCompleteTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.multiAutoCompleteTextView(id, theme, initView)
}

inline fun Ui.multiAutoCompleteTextView(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: MultiAutoCompleteTextView.() -> Unit = {}
): MultiAutoCompleteTextView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.multiAutoCompleteTextView(id, theme, initView)
}

// RatingBar

inline fun Context.ratingBar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RatingBar.() -> Unit = {}
): RatingBar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.ratingBar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RatingBar.() -> Unit = {}
): RatingBar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.ratingBar(id, theme, initView)
}

inline fun Ui.ratingBar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: RatingBar.() -> Unit = {}
): RatingBar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.ratingBar(id, theme, initView)
}

// SeekBar

inline fun Context.seekBar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: SeekBar.() -> Unit = {}
): SeekBar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(id, theme, initView)
}

inline fun View.seekBar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: SeekBar.() -> Unit = {}
): SeekBar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.seekBar(id, theme, initView)
}

inline fun Ui.seekBar(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: SeekBar.() -> Unit = {}
): SeekBar {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.seekBar(id, theme, initView)
}
