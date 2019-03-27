/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample.preview.permissions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.louiscad.splittiessample.R
import splitties.alertdialog.appcompat.coroutines.DialogButton
import splitties.alertdialog.appcompat.coroutines.showAndAwait
import kotlinx.coroutines.CancellationException
import splitties.alertdialog.appcompat.alert
import splitties.alertdialog.appcompat.message
import splitties.alertdialog.appcompat.messageResource
import splitties.alertdialog.appcompat.title
import splitties.experimental.ExperimentalSplittiesApi
import splitties.permissions.ensurePermission
import splitties.resources.txt

@UseExperimental(ExperimentalSplittiesApi::class)
suspend inline fun FragmentActivity.ensurePermission(
    permission: String,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    permission = permission,
    showRationaleAndContinueOrReturn = {
        alert {
            this.title = askDialogTitle
            this.message = askDialogMessage
        }.showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = {
        alert {
            messageResource = R.string.permission_denied_permanently_go_to_settings
        }.showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        )
    },
    returnOrThrowBlock = returnOrThrowBlock
)

@UseExperimental(ExperimentalSplittiesApi::class)
suspend inline fun Fragment.ensurePermission(
    permission: String,
    askDialogTitle: CharSequence,
    askDialogMessage: CharSequence,
    showRationaleBeforeFirstAsk: Boolean = true,
    returnButtonText: CharSequence = txt(R.string.quit),
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    permission = permission,
    showRationaleAndContinueOrReturn = {
        activity?.alert {
            this.title = askDialogTitle
            this.message = askDialogMessage
        }?.showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        ) ?: throw CancellationException() // Not fatal in coroutines world. No need to catch it.
    },
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = {
        activity?.alert {
            messageResource = R.string.permission_denied_permanently_go_to_settings
        }?.showAndAwait(
            okValue = true,
            negativeButton = DialogButton(returnButtonText, false),
            dismissValue = true
        ) ?: throw CancellationException() // Not fatal in coroutines world. No need to catch it.
    },
    returnOrThrowBlock = returnOrThrowBlock
)
