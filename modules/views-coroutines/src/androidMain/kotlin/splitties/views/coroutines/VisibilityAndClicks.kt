/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.coroutines

import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlinx.coroutines.suspendCancellableCoroutine
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.resume

/**
 * Awaits a click to be performed on this [View], then resumes.
 * This function is cancellable.
 *
 * By default, [disableAfterClick] is set to `true`, and that will make this View to be enabled
 * when this function is invoked, and finally be disabled back, after click or cancellation.
 * Note that if you set this parameter to `false`, no attempt to enable the button will be made.
 *
 * The [hideAfterClick] parameter is set to `false` by default.
 *
 * If enabled, the view will be made
 * visible when this function is invoked, and finally be hidden back (visibility to [View.GONE]),
 * after click or cancellation. Note that **no transition will be performed**. If you want to take
 * advantage of [TransitionManager] (or its AndroidX version), you need to use
 * [visibleUntilClicked] instead.
 */
@ExperimentalSplittiesApi
suspend fun View.awaitOneClick(
    disableAfterClick: Boolean = true,
    hideAfterClick: Boolean = false
) = try {
    if (disableAfterClick) isEnabled = true
    if (hideAfterClick) isVisible = true
    suspendCancellableCoroutine<Unit> { continuation ->
        setOnClickListener {
            setOnClickListener(null)
            continuation.resume(Unit)
        }
    }
} finally {
    setOnClickListener(null)
    if (disableAfterClick) isEnabled = false
    if (hideAfterClick) isVisible = false
}

/**
 * Does the same as [awaitOneClick] but for long clicks. This function is cancellable.
 */
@ExperimentalSplittiesApi
suspend fun View.awaitOneLongClick(
    disableAfterClick: Boolean = true,
    hideAfterClick: Boolean = false
) = try {
    if (disableAfterClick) isEnabled = true
    if (hideAfterClick) isVisible = true
    suspendCancellableCoroutine<Unit> { continuation ->
        setOnLongClickListener {
            setOnLongClickListener(null)
            continuation.resume(Unit); true
        }
    }
} finally {
    setOnLongClickListener(null)
    if (disableAfterClick) isEnabled = false
    if (hideAfterClick) isVisible = false
}

/**
 * Makes this [View] visible, suspends until it is clicked, executes [onClick] and returns its
 * result just after hiding the View again. By default, its final visibility is [View.GONE], but
 * if you set [finallyInvisible] to `true`, the visibility will be set to [View.INVISIBLE] instead.
 *
 * This function is cancellable.
 *
 * The [onClick] lambda is here so you can call [TransitionManager.beginDelayedTransition] or its
 * AndroidX version before the View visibility changes, but it can also fit other use cases,
 * including returning a custom value.
 */
@ExperimentalSplittiesApi
suspend inline fun <R> View.visibleUntilClicked(
    disableAfterClick: Boolean = true,
    finallyInvisible: Boolean = false,
    onClick: () -> R
): R = visibleInScope(finallyInvisible) {
    awaitOneClick(disableAfterClick = disableAfterClick)
    onClick()
}

/**
 * Does the same as [visibleUntilClicked] but for long clicks. This function is cancellable.
 */
@ExperimentalSplittiesApi
suspend inline fun <R> View.visibleUntilLongClicked(
    disableAfterClick: Boolean = true,
    finallyInvisible: Boolean = false,
    onClick: () -> R
): R = visibleInScope(finallyInvisible) {
    awaitOneLongClick(disableAfterClick = disableAfterClick)
    onClick()
}

/**
 * Makes this [View] visible, executes [block] and returns its result just after hiding back the
 * View. By default, the final visibility, is [View.GONE], but if you set [finallyInvisible] to
 * `true`, it'll be [View.INVISIBLE] instead.
 *
 * This function is meant to be used in a coroutine, where there is at least one suspend call in
 * the [block]. If the [block] is cancelled or throws, the visibility will be finally changed back,
 * according to the [finallyInvisible] argument.
 *
 * @see goneInScope
 * @see invisibleInScope
 */
@ExperimentalSplittiesApi
@OptIn(ExperimentalContracts::class)
inline fun <R> View.visibleInScope(finallyInvisible: Boolean = false, block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        isVisible = true
        block()
    } finally {
        visibility = if (finallyInvisible) View.INVISIBLE else View.GONE
    }
}

/**
 * Makes this [View] gone (visibility to [View.GONE]), executes [block] and returns its result
 * just after showing back the View.
 *
 * This function is meant to be used in a coroutine, where there is at least one suspend call in
 * the [block]. If the [block] is cancelled or throws, the visibility will be finally changed back
 * to [View.VISIBLE].
 *
 * @see visibleInScope
 * @see invisibleInScope
 */
@ExperimentalSplittiesApi
@OptIn(ExperimentalContracts::class)
inline fun <R> View.goneInScope(block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        isVisible = false
        block()
    } finally {
        isVisible = true
    }
}

/**
 * Makes this [View] invisible (visibility to [View.INVISIBLE]), executes [block] and returns its
 * result just after showing back the View.
 *
 * This function is meant to be used in a coroutine, where there is at least one suspend call in
 * the [block]. If the [block] is cancelled or throws, the visibility will be finally changed back
 * to [View.VISIBLE].
 *
 * @see visibleInScope
 * @see goneInScope
 */
@ExperimentalSplittiesApi
@OptIn(ExperimentalContracts::class)
inline fun <R> View.invisibleInScope(block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        isInvisible = true
        block()
    } finally {
        isInvisible = false
    }
}
