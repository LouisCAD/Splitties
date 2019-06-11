/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.coroutines.material

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Enables this [FloatingActionButton], shows it, waits for the user to click it, and finally
 * disables it and hides it. This function is cancellable.
 *
 * Uses the [FloatingActionButton.show] and [FloatingActionButton.hide] functions which animate
 * the FAB when changing its visibility.
 *
 * If [disableAfterClick] is set to `false`, the enabled state of this [FloatingActionButton] will
 * be left unchanged.
 */
suspend fun FloatingActionButton.showAndAwaitOneClickThenHide(
    disableAfterClick: Boolean = true
) = try {
    if (disableAfterClick) isEnabled = true
    show()
    suspendCancellableCoroutine<Unit> { continuation ->
        setOnClickListener {
            setOnClickListener(null)
            continuation.resume(Unit)
        }
    }
} finally {
    setOnClickListener(null)
    if (disableAfterClick) isEnabled = false
    hide()
}
