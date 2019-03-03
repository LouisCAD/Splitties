/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.snackbar

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback

/**
 * The `event` parameter of [block] is one of:
 * [BaseCallback.DISMISS_EVENT_SWIPE], [BaseCallback.DISMISS_EVENT_ACTION],
 * [BaseCallback.DISMISS_EVENT_TIMEOUT], [BaseCallback.DISMISS_EVENT_MANUAL] or
 * [BaseCallback.DISMISS_EVENT_CONSECUTIVE].
 */
inline fun Snackbar.onDismiss(crossinline block: (event: Int) -> Unit) {
    addCallback(object : Snackbar.Callback() {
        override fun onDismissed(snackbar: Snackbar?, event: Int) {
            block(event)
        }
    })
}
