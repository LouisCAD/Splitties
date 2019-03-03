/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample.extensions.coroutines

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import splitties.resources.appTxt
import kotlin.coroutines.resume

class DialogButton<T>(val text: CharSequence, val value: T) {
    @Suppress("NOTHING_TO_INLINE")
    companion object {
        inline fun ok(): DialogButton<Unit> = ok(Unit)
        fun <T> ok(value: T) = DialogButton(appTxt(android.R.string.ok), value)
        fun <T> cancel(value: T = throwDialogCancellationException()): DialogButton<T> {
            return DialogButton(appTxt(android.R.string.cancel), value)
        }
    }
}

class DialogCancellationException(val sourceButton: Int?) : CancellationException()

@Throws(DialogCancellationException::class)
suspend fun <R> AlertDialog.showAndAwait(
    positiveButton: DialogButton<R>? = null,
    negativeButton: DialogButton<R>? = null,
    neutralButton: DialogButton<R>? = null,
    dismissValue: R = throwDialogCancellationException()
): R = suspendCancellableCoroutine { c ->
    val clickListener = DialogInterface.OnClickListener { _, which ->
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> positiveButton
            DialogInterface.BUTTON_NEUTRAL -> neutralButton
            DialogInterface.BUTTON_NEGATIVE -> negativeButton
            else -> null
        }?.apply {
            if (value === cancelToken) c.cancel(DialogCancellationException(which))
            else c.resume(value)
        }
    }
    positiveButton?.let { setButton(DialogInterface.BUTTON_POSITIVE, it.text, clickListener) }
    neutralButton?.let { setButton(DialogInterface.BUTTON_NEUTRAL, it.text, clickListener) }
    negativeButton?.let { setButton(DialogInterface.BUTTON_NEGATIVE, it.text, clickListener) }
    setOnDismissListener {
        if (dismissValue === cancelToken) c.cancel(DialogCancellationException(null))
        else runCatching { c.resume(dismissValue) } // Resuming twice throws, but we can ignore it.
    }
    show()
    c.invokeOnCancellation { dismiss() }
}

private val cancelToken = Any()
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
private inline fun <T> throwDialogCancellationException() = cancelToken as T
