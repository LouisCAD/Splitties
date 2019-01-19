package com.louiscad.splittiessample.extensions.coroutines

import android.view.View
import androidx.core.view.isVisible
import com.louiscad.splittiessample.extensions.visibleInScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun View.awaitOneClick(
    disableAfterClick: Boolean = true,
    hideAfterClick: Boolean = false
) = try {
    if (disableAfterClick) isEnabled = true
    if (hideAfterClick) isVisible = true
    suspendCancellableCoroutine<Unit> { continuation ->
        setOnClickListener { continuation.resume(Unit) }
    }
} finally {
    setOnClickListener(null)
    if (disableAfterClick) isEnabled = false
    if (hideAfterClick) isVisible = false
}

suspend inline fun <R> View.visibleUntilClicked(
    disableAfterClick: Boolean = true,
    finallyInvisible: Boolean = false,
    onClick: () -> R
): R = visibleInScope(finallyInvisible) {
    awaitOneClick(disableAfterClick = disableAfterClick)
    onClick()
}
