/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.alertdialog.appcompat

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import kotlin.DeprecationLevel.HIDDEN

/**
 * Instantiates an [AlertDialog.Builder] for the [Context], applies the [dialogConfig] lambda to
 * it, then creates an [AlertDialog] from the builder, and returns it, so you can call
 * [AlertDialog.show] on the created dialog.
 */
inline fun Context.alertDialog(dialogConfig: AlertDialog.Builder.() -> Unit): AlertDialog {
    return AlertDialog.Builder(this)
        .apply(dialogConfig)
        .create()
}

/**
 * Instantiates an [AlertDialog.Builder] for the [Context], sets the passed [title], [message] and
 * [iconResource], applies the [dialogConfig] lambda to it, then creates an [AlertDialog] from
 * the builder, and returns it, so you can call [AlertDialog.show] on the created dialog.
 */
inline fun Context.alertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    @DrawableRes iconResource: Int = 0,
    dialogConfig: AlertDialog.Builder.() -> Unit = {}
): AlertDialog {
    return AlertDialog.Builder(this).apply {
        this.title = title
        this.message = message
        setIcon(iconResource)
        dialogConfig()
    }.create()
}

/**
 * Instantiates an [AlertDialog.Builder] for the [Context], sets the passed [title], [message] and
 * [icon], applies the [dialogConfig] lambda to it, then creates an [AlertDialog] from
 * the builder, and returns it, so you can call [AlertDialog.show] on the created dialog.
 */
inline fun Context.alertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    icon: Drawable?,
    dialogConfig: AlertDialog.Builder.() -> Unit = {}
): AlertDialog {
    return AlertDialog.Builder(this).apply {
        this.title = title
        this.message = message
        setIcon(icon)
        dialogConfig()
    }.create()
}

/**
 * Instantiates an [AlertDialog.Builder] for the [Activity], applies the [dialogConfig] lambda to
 * it, then creates an [AlertDialog] from the builder, and returns it, so you can call
 * [AlertDialog.show] on the created dialog.
 */
@Deprecated(
    message = "This function is limited to Activity only and its name is not explicit enough. " +
            "Use the alertDialog function instead.",
    replaceWith = ReplaceWith(
        expression = "this.alertDialog(dialogConfig)",
        imports = ["splitties.alertdialog.appcompat.alertDialog"]
    )
)
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
    set(value) {
        setTitle(value)
    }

/** Write only property that sets the dialog title. */
var AlertDialog.Builder.title: CharSequence?
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setTitle(value)
    }

/** Write only property that sets the dialog message from the passed string resource. */
var AlertDialog.Builder.messageResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) {
        setMessage(value)
    }

/** Write only property that sets the dialog message. */
var AlertDialog.Builder.message: CharSequence?
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
