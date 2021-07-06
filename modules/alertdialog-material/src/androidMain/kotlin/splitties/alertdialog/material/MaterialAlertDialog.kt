/*
 * Copyright 2020-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:OptIn(ExperimentalContracts::class)

package splitties.alertdialog.material

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import splitties.alertdialog.appcompat.message
import splitties.alertdialog.appcompat.title
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Instantiates a [MaterialAlertDialogBuilder] for the [Context], applies the [dialogConfig] lambda to
 * it, then creates an [AlertDialog] from the builder, and returns it, so you can call
 * [AlertDialog.show] on the created dialog.
 */
inline fun Context.materialAlertDialog(dialogConfig: MaterialAlertDialogBuilder.() -> Unit): AlertDialog {
    contract { callsInPlace(dialogConfig, InvocationKind.EXACTLY_ONCE) }
    return MaterialAlertDialogBuilder(this)
        .apply(dialogConfig)
        .create()
}

/**
 * Instantiates a [MaterialAlertDialogBuilder] for the [Context], sets the passed [title], [message] and
 * [iconResource], applies the [dialogConfig] lambda to it, then creates an [AlertDialog] from
 * the builder, and returns it, so you can call [AlertDialog.show] on the created dialog.
 */
inline fun Context.materialAlertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    @DrawableRes iconResource: Int = 0,
    isCancellable: Boolean = true,
    dialogConfig: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog {
    contract { callsInPlace(dialogConfig, InvocationKind.EXACTLY_ONCE) }
    return materialAlertDialog(
        title = title,
        message = message,
        icon = null,
        isCancellable = isCancellable,
    ) {
        setIcon(iconResource)
        dialogConfig()
    }
}

/**
 * Instantiates a [MaterialAlertDialogBuilder] for the [Context], sets the passed [title], [message] and
 * [icon], applies the [dialogConfig] lambda to it, then creates an [AlertDialog] from
 * the builder, and returns it, so you can call [AlertDialog.show] on the created dialog.
 */
inline fun Context.materialAlertDialog(
    title: CharSequence? = null,
    message: CharSequence? = null,
    icon: Drawable? = null,
    isCancellable: Boolean = true,
    dialogConfig: MaterialAlertDialogBuilder.() -> Unit = {}
): AlertDialog {
    contract { callsInPlace(dialogConfig, InvocationKind.EXACTLY_ONCE) }
    return MaterialAlertDialogBuilder(this).apply {
        this.title = title
        this.message = message
        setIcon(icon)
        setCancelable(isCancellable)
        dialogConfig()
    }.create()
}

/** Write only property that sets backgroundInsetStart. */
inline var MaterialAlertDialogBuilder.backgroundInsetStart: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetStart(value)
    }

/** Write only property that sets backgroundInsetTop. */
inline var MaterialAlertDialogBuilder.backgroundInsetTop: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetTop(value)
    }

/** Write only property that sets backgroundInsetEnd. */
inline var MaterialAlertDialogBuilder.backgroundInsetEnd: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetEnd(value)
    }

/** Write only property that sets backgroundInsetBottom. */
inline var MaterialAlertDialogBuilder.backgroundInsetBottom: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(value) {
        setBackgroundInsetBottom(value)
    }
