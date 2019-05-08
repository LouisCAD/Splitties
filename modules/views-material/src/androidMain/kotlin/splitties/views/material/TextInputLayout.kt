/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.material

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

var TextInputLayout.text: CharSequence?
    get() = editTextNow.text
    set(value) = editTextNow.setText(value)

val TextInputLayout.string: String
    get() = editTextNow.text.toString()

private inline val TextInputLayout.editTextNow: EditText
    get() = editText ?: error("No EditText found! Make sure it has been added first.")
