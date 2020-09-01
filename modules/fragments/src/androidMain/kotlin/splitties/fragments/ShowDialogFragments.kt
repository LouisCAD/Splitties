/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.fragments

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.lifecycle.coroutines.awaitResumed

/**
 * Shows the [DialogFragment] created in [newDialogRef] as soon as [lifecycle] is in
 * [Lifecycle.State.RESUMED], immediately if possible. If the lifecycle is not in the resumed state
 * when this function is called and it reaches [Lifecycle.State.DESTROYED] before being resumed,
 * the [DialogFragment] will not be shown.
 *
 * You can specify the [tag] for later retrieval in [FragmentManager.findFragmentByTag], and
 * you can put arguments in the optional [setup] inline lambda.
 */
@ExperimentalSplittiesApi
inline fun <DF : DialogFragment> FragmentManager.showAsync(
    lifecycle: Lifecycle,
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
): DF = newDialogRef().apply(setup).also {
    if (isStateSaved) lifecycle.coroutineScope.launch {
        lifecycle.awaitResumed()
        it.show(this@showAsync, tag)
    } else it.show(this, tag)
}

/**
 * Shows the [DialogFragment] created in [newDialogRef] as soon as this [Fragment]'s lifecycle is in
 * [Lifecycle.State.RESUMED], immediately if possible. If the lifecycle is not in the resumed state
 * when this function is called and it reaches [Lifecycle.State.DESTROYED] before being resumed,
 * the [DialogFragment] will not be shown.
 *
 * You can specify the [tag] for later retrieval in [FragmentManager.findFragmentByTag], and
 * you can put arguments in the optional [setup] inline lambda.
 */
@ExperimentalSplittiesApi
inline fun <DF : DialogFragment> Fragment.showAsync(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
): DF = parentFragmentManager.showAsync(lifecycle, newDialogRef, tag, setup)

/**
 * Shows the [DialogFragment] created in [newDialogRef] as soon as this Activity's lifecycle is in
 * [Lifecycle.State.RESUMED], immediately if possible. If the lifecycle is not in the resumed state
 * when this function is called and it reaches [Lifecycle.State.DESTROYED] before being resumed,
 * the [DialogFragment] will not be shown.
 *
 * You can specify the [tag] for later retrieval in [FragmentManager.findFragmentByTag], and
 * you can put arguments in the optional [setup] inline lambda.
 */
@ExperimentalSplittiesApi
inline fun <DF : DialogFragment> FragmentActivity.showAsync(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
): DF = supportFragmentManager.showAsync(lifecycle, newDialogRef, tag, setup)

/**
 * Shows the [DialogFragment] created in [newDialogRef] as soon as [lifecycle] is in
 * [Lifecycle.State.RESUMED], immediately if possible. If the lifecycle is not in the resumed state
 * when this function is called and it reaches [Lifecycle.State.DESTROYED] before being resumed,
 * the [DialogFragment] will not be shown.
 *
 * You can specify the [tag] for later retrieval in [FragmentManager.findFragmentByTag], and
 * you can put arguments in the optional [setup] inline lambda.
 *
 * This functions resumes only after the [DialogFragment] has been shown, unless you set
 * [commitNowWhenResumed] to false, in which case it'll be shown after [FragmentManager] executes
 * its pending transactions.
 */
suspend inline fun <DF : DialogFragment> FragmentManager.show(
    lifecycle: Lifecycle,
    newDialogRef: () -> DF,
    tag: String? = null,
    commitNowWhenResumed: Boolean = true,
    setup: DF.() -> Unit = {}
): DF = newDialogRef().apply(setup).also {
    if (isStateSaved) {
        @OptIn(ExperimentalSplittiesApi::class)
        lifecycle.awaitResumed()
        if (commitNowWhenResumed) it.showNow(this, tag) else it.show(this, tag)
    } else if (commitNowWhenResumed) it.showNow(this, tag) else it.show(this, tag)
}

/**
 * Shows the [DialogFragment] created in [newDialogRef] as soon as this [Fragment]'s lifecycle is in
 * [Lifecycle.State.RESUMED], immediately if possible. If the lifecycle is not in the resumed state
 * when this function is called and it reaches [Lifecycle.State.DESTROYED] before being resumed,
 * the [DialogFragment] will not be shown.
 *
 * You can specify the [tag] for later retrieval in [FragmentManager.findFragmentByTag], and
 * you can put arguments in the optional [setup] inline lambda.
 *
 * This functions resumes only after the [DialogFragment] has been shown, unless you set
 * [commitNowWhenResumed] to false, in which case it'll be shown after [FragmentManager] executes
 * its pending transactions.
 */
suspend inline fun <DF : DialogFragment> Fragment.show(
    newDialogRef: () -> DF,
    tag: String? = null,
    commitNowWhenResumed: Boolean = true,
    setup: DF.() -> Unit = {}
): DF = parentFragmentManager.show(lifecycle, newDialogRef, tag, commitNowWhenResumed, setup)

/**
 * Shows the [DialogFragment] created in [newDialogRef] as soon as this Activity's lifecycle is in
 * [Lifecycle.State.RESUMED], immediately if possible. If the lifecycle is not in the resumed state
 * when this function is called and it reaches [Lifecycle.State.DESTROYED] before being resumed,
 * the [DialogFragment] will not be shown.
 *
 * You can specify the [tag] for later retrieval in [FragmentManager.findFragmentByTag], and
 * you can put arguments in the optional [setup] inline lambda.
 *
 * This functions resumes only after the [DialogFragment] has been shown, unless you set
 * [commitNowWhenResumed] to false, in which case it'll be shown after [FragmentManager] executes
 * its pending transactions.
 */
suspend inline fun <DF : DialogFragment> FragmentActivity.show(
    newDialogRef: () -> DF,
    tag: String? = null,
    commitNowWhenResumed: Boolean = true,
    setup: DF.() -> Unit = {}
): DF = supportFragmentManager.show(lifecycle, newDialogRef, tag, commitNowWhenResumed, setup)
