/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:OptIn(ExperimentalSplittiesApi::class)

package com.example.splitties.extensions.permissions

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.splitties.R
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.coroutines.DialogButton
import splitties.alertdialog.appcompat.coroutines.showAndAwait
import splitties.experimental.ExperimentalSplittiesApi
import splitties.permissions.ensureAllPermissions
import splitties.permissions.ensurePermission
import splitties.resources.txt

suspend inline fun FragmentActivity.ensurePermission(
    permission: String,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    activity = this,
    fragmentManager = supportFragmentManager,
    lifecycle = lifecycle,
    permission = permission,
    askDialogTitle = askDialogTitle,
    askDialogMessage = askDialogMessage,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    returnButtonText = returnButtonText,
    returnOrThrowBlock = returnOrThrowBlock
)

suspend inline fun Fragment.ensurePermission(
    permission: String,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    activity = requireActivity(),
    fragmentManager = parentFragmentManager,
    lifecycle = lifecycle,
    permission = permission,
    askDialogTitle = askDialogTitle,
    askDialogMessage = askDialogMessage,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    returnButtonText = returnButtonText,
    returnOrThrowBlock = returnOrThrowBlock
)

suspend inline fun ComponentActivity.ensurePermission(
    permission: String,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    permission = permission,
    showRationaleAndContinueOrReturn = {
        alertDialog(
            title = askDialogTitle,
            message = askDialogMessage
        ).showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = {
        alertDialog(
            message = txt(R.string.permission_denied_permanently_go_to_settings)
        ).showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    returnOrThrowBlock = returnOrThrowBlock
)

suspend inline fun ComponentActivity.ensureAllPermissions(
    permissions: List<String>,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    permissions = permissions,
    showRationaleAndContinueOrReturn = {
        alertDialog(
            title = askDialogTitle,
            message = askDialogMessage
        ).showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = {
        alertDialog(
            message = txt(R.string.permission_denied_permanently_go_to_settings)
        ).showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    returnOrThrowBlock = returnOrThrowBlock
)

suspend inline fun ensurePermission(
    activity: Activity,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    permission: String,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = activity.txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    activity = activity,
    fragmentManager = fragmentManager,
    lifecycle = lifecycle,
    permission = permission,
    showRationaleAndContinueOrReturn = {
        activity.alertDialog(
            title = askDialogTitle,
            message = askDialogMessage
        ).showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = {
        activity.alertDialog(
            message = activity.txt(R.string.permission_denied_permanently_go_to_settings)
        ).showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    returnOrThrowBlock = returnOrThrowBlock
)
