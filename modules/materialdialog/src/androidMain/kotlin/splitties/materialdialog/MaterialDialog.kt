/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.materialdialog

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import splitties.alertdialog.appcompat.message
import splitties.alertdialog.appcompat.title

/**
 * Instantiates an [MaterialAlertDialogBuilder] for the [Context], applies the [dialogConfig] lambda to
 * it, then creates an [AlertDialog] from the builder, and returns it, so you can call
 * [AlertDialog.show] on the created dialog.
 */
inline fun Context.materialDialog(dialogConfig: MaterialAlertDialogBuilder.() -> Unit): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .apply(dialogConfig)
        .create()
}

/**
 * Instantiates an [MaterialAlertDialogBuilder] for the [Context], sets the passed [title], [message] and
 * [iconResource], applies the [dialogConfig] lambda to it, then creates an [AlertDialog] from
 * the builder, and returns it, so you can call [AlertDialog.show] on the created dialog.
 */
inline fun Context.materialDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    @DrawableRes iconResource: Int = 0,
    dialogConfig: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog {
    return MaterialAlertDialogBuilder(this).apply {
        this.title = title
        this.message = message
        setIcon(iconResource)
        dialogConfig()
    }.create()
}

/**
 * Instantiates an [MaterialAlertDialogBuilder] for the [Context], sets the passed [title], [message] and
 * [icon], applies the [dialogConfig] lambda to it, then creates an [AlertDialog] from
 * the builder, and returns it, so you can call [AlertDialog.show] on the created dialog.
 */
inline fun Context.materialDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    icon: Drawable? = null,
    dialogConfig: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog {
    return MaterialAlertDialogBuilder(this).apply {
        this.title = title
        this.message = message
        setIcon(icon)
        dialogConfig()
    }.create()
}

/** Write only property that sets backgroundInsetStart. */
var MaterialAlertDialogBuilder.backgroundInsetStart: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetStart(value)
    }

/** Write only property that sets backgroundInsetEnd. */
var MaterialAlertDialogBuilder.backgroundInsetEnd: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetEnd(value)
    }

/** Write only property that sets backgroundInsetTop. */
var MaterialAlertDialogBuilder.backgroundInsetTop: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetTop(value)
    }

/** Write only property that sets backgroundInsetBottom. */
var MaterialAlertDialogBuilder.backgroundInsetBottom: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetBottom(value)
    }
