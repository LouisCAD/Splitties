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

package splitties.alertdialog

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.widget.Button
import kotlin.DeprecationLevel.HIDDEN

inline fun Activity.alert(dialogConfig: AlertDialog.Builder.() -> Unit): AlertDialog {
    return AlertDialog.Builder(this)
            .apply(dialogConfig)
            .create()
}

inline fun AlertDialog.onShow(crossinline onShowListener: AlertDialog.() -> Unit) = apply {
    setOnShowListener { onShowListener() }
}

@RequiresApi(JELLY_BEAN_MR1)
inline fun AlertDialog.Builder.onDismiss(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setOnDismissListener { handler(it) }
}

val AlertDialog.positiveButton: Button inline get() = getButton(AlertDialog.BUTTON_POSITIVE)
val AlertDialog.neutralButton: Button inline get() = getButton(AlertDialog.BUTTON_NEUTRAL)
val AlertDialog.negativeButton: Button inline get() = getButton(AlertDialog.BUTTON_NEGATIVE)

var AlertDialog.Builder.titleResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setTitle(value)
    }

var AlertDialog.Builder.title: CharSequence
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setTitle(value)
    }

var AlertDialog.Builder.messageResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setMessage(value)
    }

var AlertDialog.Builder.message: CharSequence
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setMessage(value)
    }

inline fun AlertDialog.Builder.okButton(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setPositiveButton(android.R.string.ok, { dialog: DialogInterface, _: Int -> handler(dialog) })
}

@Suppress("NOTHING_TO_INLINE")
inline fun AlertDialog.Builder.okButton() {
    setPositiveButton(android.R.string.ok, null)
}

inline fun AlertDialog.Builder.cancelButton(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setNegativeButton(android.R.string.cancel, { dialog: DialogInterface, _: Int -> handler(dialog) })
}

@Suppress("NOTHING_TO_INLINE")
inline fun AlertDialog.Builder.cancelButton() {
    setNegativeButton(android.R.string.cancel, null)
}

inline fun AlertDialog.Builder.positiveButton(@StringRes textResId: Int, crossinline handler: (dialog: DialogInterface) -> Unit) {
    setPositiveButton(textResId, { dialog: DialogInterface, _: Int -> handler(dialog) })
}

inline fun AlertDialog.Builder.neutralButton(@StringRes textResId: Int, crossinline handler: (dialog: DialogInterface) -> Unit) {
    setNeutralButton(textResId, { dialog: DialogInterface, _: Int -> handler(dialog) })
}

inline fun AlertDialog.Builder.negativeButton(@StringRes textResId: Int, crossinline handler: (dialog: DialogInterface) -> Unit) {
    setNegativeButton(textResId, { dialog: DialogInterface, _: Int -> handler(dialog) })
}
