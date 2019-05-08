/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.appcompat.experimental

import android.content.Context
import android.view.View
import android.widget.*
import androidx.annotation.AttrRes
import androidx.appcompat.widget.*
import splitties.experimental.InternalSplittiesApi
import splitties.views.appcompat.Toolbar
import androidx.appcompat.widget.Toolbar as AndroidXToolbar

/**
 * Matches [androidx.appcompat.app.AppCompatViewInflater.createView] content plus other AppCompat
 * only views.
 */
@InternalSplittiesApi
inline fun <reified V : View> instantiateAppCompatView(
    clazz: Class<out V>,
    context: Context
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
    // AppCompat only views below
    AndroidXToolbar::class.java -> Toolbar(context)
    Toolbar::class.java -> Toolbar(context)
    else -> null
} as V?

/** Matches [androidx.appcompat.app.AppCompatViewInflater.createView] content. */
@InternalSplittiesApi
inline fun <reified V : View> instantiateThemeAttrStyledAppCompatView(
    clazz: Class<out V>,
    context: Context,
    @AttrRes styleThemeAttribute: Int
): V? = when (clazz) {
    TextView::class.java -> AppCompatTextView(context, null, styleThemeAttribute)
    Button::class.java -> AppCompatButton(context, null, styleThemeAttribute)
    ImageView::class.java -> AppCompatImageView(context, null, styleThemeAttribute)
    EditText::class.java -> AppCompatEditText(context, null, styleThemeAttribute)
    Spinner::class.java -> AppCompatSpinner(context, null, styleThemeAttribute)
    ImageButton::class.java -> AppCompatImageButton(context, null, styleThemeAttribute)
    CheckBox::class.java -> AppCompatCheckBox(context, null, styleThemeAttribute)
    RadioButton::class.java -> AppCompatRadioButton(context, null, styleThemeAttribute)
    CheckedTextView::class.java -> AppCompatCheckedTextView(context, null, styleThemeAttribute)
    AutoCompleteTextView::class.java -> {
        AppCompatAutoCompleteTextView(context, null, styleThemeAttribute)
    }
    MultiAutoCompleteTextView::class.java -> {
        AppCompatMultiAutoCompleteTextView(context, null, styleThemeAttribute)
    }
    RatingBar::class.java -> AppCompatRatingBar(context, null, styleThemeAttribute)
    SeekBar::class.java -> AppCompatSeekBar(context, null, styleThemeAttribute)
    // AppCompat only views below
    AndroidXToolbar::class.java -> Toolbar(context, null, styleThemeAttribute)
    Toolbar::class.java -> Toolbar(context, null, styleThemeAttribute)
    else -> null
} as V?
