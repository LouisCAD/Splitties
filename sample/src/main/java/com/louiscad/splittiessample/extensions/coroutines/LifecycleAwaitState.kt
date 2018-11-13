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

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Lifecycle.awaitState(state: Lifecycle.State) {
    require(state != Lifecycle.State.DESTROYED) {
        "DESTROYED is a terminal state that is forbidden for awaitState(…), to avoid leaks."
    }
    if (currentState.isAtLeast(state)) return // Fast path
    suspendCancellableCoroutine<Unit> { c ->
        if (currentState == Lifecycle.State.DESTROYED) { // Fast path to cancellation
            c.cancel()
            return@suspendCancellableCoroutine
        }
        val observer = object : GenericLifecycleObserver {
            override fun onStateChanged(source: LifecycleOwner?, event: Lifecycle.Event) {
                if (currentState.isAtLeast(state)) {
                    removeObserver(this)
                    c.resume(Unit)
                } else if (currentState == Lifecycle.State.DESTROYED) {
                    c.cancel()
                }
            }
        }
        addObserver(observer)
        c.invokeOnCancellation { removeObserver(observer) }
    }
}
