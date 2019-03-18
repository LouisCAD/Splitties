/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)

package splitties.fragments

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.RESUMED
import kotlinx.coroutines.launch
import splitties.experimental.ExperimentalSplittiesApi
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi
import splitties.lifecycle.coroutines.awaitState
import splitties.lifecycle.coroutines.coroutineScope

@ExperimentalSplittiesApi
inline fun <DF : DialogFragment> FragmentManager.showAsync(
    lifecycle: Lifecycle,
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = newDialogRef().apply(setup).also {
    if (isStateSaved) lifecycle.coroutineScope.launch {
        lifecycle.awaitState(RESUMED)
        it.show(this@showAsync, tag)
    } else it.show(this, tag)
}

@ExperimentalSplittiesApi
inline fun <DF : DialogFragment> Fragment.showAsync(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = fragmentManager!!.showAsync(lifecycle, newDialogRef, tag, setup)

@ExperimentalSplittiesApi
inline fun <DF : DialogFragment> FragmentActivity.showAsync(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = supportFragmentManager.showAsync(lifecycle, newDialogRef, tag, setup)

suspend inline fun <DF : DialogFragment> FragmentManager.show(
    lifecycle: Lifecycle,
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = newDialogRef().apply(setup).also {
    if (isStateSaved) {
        lifecycle.awaitState(RESUMED)
        it.show(this, tag)
    } else it.show(this, tag)
}

suspend inline fun <DF : DialogFragment> Fragment.show(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = fragmentManager!!.show(lifecycle, newDialogRef, tag, setup)

suspend inline fun <DF : DialogFragment> FragmentActivity.show(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = supportFragmentManager.show(lifecycle, newDialogRef, tag, setup)
