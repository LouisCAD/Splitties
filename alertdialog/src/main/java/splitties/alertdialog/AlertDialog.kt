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
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.widget.Button
import kotlin.DeprecationLevel.HIDDEN

/**
 * Instantiates an [AlertDialog.Builder] for the [Activity], applies the [dialogConfig] lambda to
 * it, then creates an [AlertDialog] from the builder, and returns it, so you can call
 * [AlertDialog.show] on the created dialog.
 */
inline fun Activity.alert(dialogConfig: AlertDialog.Builder.() -> Unit): AlertDialog {
    return AlertDialog.Builder(this)
        .apply(dialogConfig)
        .create()
}

/**
 * Sets the [AlertDialog]'s [DialogInterface.OnShowListener] so it calls the passed
 * [onShowListener] lambda when the dialog is shown, and returns the same instance for chained
 * calls.
 */
inline fun AlertDialog.onShow(crossinline onShowListener: AlertDialog.() -> Unit) = apply {
    setOnShowListener { onShowListener() }
}

/**
 * Sets the [AlertDialog]'s [DialogInterface.OnDismissListener] so it calls the passed [handler]
 * lambda when the dialog is dismissed.
 */
@RequiresApi(17)
inline fun AlertDialog.Builder.onDismiss(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setOnDismissListener { handler(it) }
}

/**
 * Returns the positive button from the [AlertDialog], or throws an [IllegalArgumentException] if
 * the dialog has no positive button, or has not been shown yet.
 *
 * You can use the [onShow] extension function to wait for the dialog to be shown and be able to
 * access the button.
 */
inline val AlertDialog.positiveButton: Button
    get() = requireNotNull(getButton(AlertDialog.BUTTON_POSITIVE)) {
        "The dialog has no positive button or has not been shown yet."
    }

/**
 * Returns the neutral button from the [AlertDialog], or throws an [IllegalArgumentException] if
 * the dialog has no neutral button, or has not been shown yet.
 *
 * You can use the [onShow] extension function to wait for the dialog to be shown and be able to
 * access the button.
 */
inline val AlertDialog.neutralButton: Button
    get() = requireNotNull(getButton(AlertDialog.BUTTON_NEUTRAL)) {
        "The dialog has no neutral button or has not been shown yet."
    }

/**
 * Returns the negative button from the [AlertDialog], or throws an [IllegalArgumentException] if
 * the dialog has no negative button, or has not been shown yet.
 *
 * You can use the [onShow] extension function to wait for the dialog to be shown and be able to
 * access the button.
 */
inline val AlertDialog.negativeButton: Button
    get() = requireNotNull(getButton(AlertDialog.BUTTON_NEGATIVE)) {
        "The dialog has no negative button or has not been shown yet."
    }

/** Write only property that sets the dialog title from the passed string resource. */
var AlertDialog.Builder.titleResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@StringRes value) {
        setTitle(value)
    }

/** Write only property that sets the dialog title. */
var AlertDialog.Builder.title: CharSequence
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setTitle(value)
    }

/** Write only property that sets the dialog message from the passed string resource. */
var AlertDialog.Builder.messageResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@StringRes value) {
        setMessage(value)
    }

/** Write only property that sets the dialog message. */
var AlertDialog.Builder.message: CharSequence
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setMessage(value)
    }

/**
 * Sets a positive button with [android.R.string.ok] ("OK", with translations) as its text, and
 * calls the passed [handler] lambda when it is clicked.
 */
inline fun AlertDialog.Builder.okButton(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setPositiveButton(android.R.string.ok) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

/**
 * Sets a positive button with [android.R.string.ok] ("OK", with translations) as its text. A click
 * on the button will have no effect apart from dismissing the dialog and having its
 * [DialogInterface.OnDismissListener] called, if any was set (e.g. using [onDismiss]).
 */
@Suppress("NOTHING_TO_INLINE")
inline fun AlertDialog.Builder.okButton() {
    setPositiveButton(android.R.string.ok, null)
}

/**
 * Sets a negative button with [android.R.string.cancel] ("Cancel", with translations) as its text,
 * and calls the passed [handler] lambda when it is clicked.
 */
inline fun AlertDialog.Builder.cancelButton(crossinline handler: (dialog: DialogInterface) -> Unit) {
    setNegativeButton(android.R.string.cancel) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

/**
 * Sets a negative button with [android.R.string.cancel] ("Cancel", with translations) as its text.
 * A click on the button will have no effect apart from dismissing the dialog and having its
 * [DialogInterface.OnDismissListener] called, if any was set (e.g. using [onDismiss]).
 */
@Suppress("NOTHING_TO_INLINE")
inline fun AlertDialog.Builder.cancelButton() {
    setNegativeButton(android.R.string.cancel, null)
}

/**
 * Sets a positive button with the passed [textResId] text resource, and calls the passed [handler]
 * lambda when it is clicked.
 */
inline fun AlertDialog.Builder.positiveButton(
    @StringRes textResId: Int,
    crossinline handler: (dialog: DialogInterface) -> Unit
) {
    setPositiveButton(textResId) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

/**
 * Sets a neutral button with the passed [textResId] text resource, and calls the passed [handler]
 * lambda when it is clicked.
 */
inline fun AlertDialog.Builder.neutralButton(
    @StringRes textResId: Int,
    crossinline handler: (dialog: DialogInterface) -> Unit
) {
    setNeutralButton(textResId) { dialog: DialogInterface, _: Int -> handler(dialog) }
}

/**
 * Sets a negative button with the passed [textResId] text resource, and calls the passed [handler]
 * lambda when it is clicked.
 */
inline fun AlertDialog.Builder.negativeButton(
    @StringRes textResId: Int,
    crossinline handler: (dialog: DialogInterface) -> Unit
) {
    setNegativeButton(textResId) { dialog: DialogInterface, _: Int -> handler(dialog) }
}
