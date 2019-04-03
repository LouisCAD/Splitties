/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.coroutines.material

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FloatingActionButton.showAndAwaitOneClickThenHide(
    disableAfterClick: Boolean = true
) = try {
    if (disableAfterClick) isEnabled = true
    show()
    suspendCancellableCoroutine<Unit> { continuation ->
        setOnClickListener { continuation.resume(Unit) }
    }
} finally {
    setOnClickListener(null)
    if (disableAfterClick) isEnabled = false
    hide()
}
