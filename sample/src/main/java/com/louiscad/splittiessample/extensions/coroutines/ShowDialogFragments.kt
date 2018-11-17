/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.louiscad.splittiessample.extensions.coroutines

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.State.RESUMED
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import kotlinx.coroutines.launch

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

inline fun <DF : DialogFragment> Fragment.showAsync(
    newDialogRef: () -> DF,
    tag: String? = null,
    setup: DF.() -> Unit = {}
) = fragmentManager!!.showAsync(lifecycle, newDialogRef, tag, setup)

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
