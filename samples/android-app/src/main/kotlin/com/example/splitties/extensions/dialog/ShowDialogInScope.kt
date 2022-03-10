/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.dialog

import android.app.Dialog
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.suspendCancellableCoroutine
import splitties.coroutines.raceOf
import kotlin.coroutines.resume

suspend fun <R> Dialog.showInScope(
    dismissValue: R,
    block: suspend () -> R
): R = try {
    raceOf({
        suspendCancellableCoroutine<R> { c ->
            setOnDismissListener { c.resume(dismissValue) }
            show()
        }
    }, {
        block()
    })
} finally {
    dismiss()
}

suspend fun <R> Dialog.showInScope(
    onDismiss: suspend () -> R,
    block: suspend () -> R
): R = try {
    coroutineScope {
        @OptIn(ExperimentalCoroutinesApi::class)
        val dismissJob = launch(start = CoroutineStart.UNDISPATCHED) {
            suspendCancellableCoroutine<Unit> { c ->
                setOnDismissListener { c.resume(Unit) }
                show()
            }
        }
        @OptIn(ExperimentalCoroutinesApi::class)
        val blockAsync = async(start = CoroutineStart.UNDISPATCHED) {
            block()
        }
        select<R> {
            blockAsync.onAwait { result ->
                dismissJob.cancel()
                result
            }
            dismissJob.onJoin {
                blockAsync.cancel()
                onDismiss()
            }
        }
    }
} finally {
    dismiss()
}

suspend fun Dialog.showInScope(
    block: suspend () -> Unit
): Unit = try {
    raceOf({
        suspendCancellableCoroutine<Unit> { c ->
            setOnDismissListener { c.resume(Unit) }
            show()
        }
    }, {
        block()
    })
} finally {
    dismiss()
}
