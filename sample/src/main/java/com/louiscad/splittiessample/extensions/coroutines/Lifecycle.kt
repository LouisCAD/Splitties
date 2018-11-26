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
import android.arch.lifecycle.Lifecycle.State.INITIALIZED
import android.arch.lifecycle.LifecycleOwner
import kotlinx.coroutines.*

/**
 * Returns a [CoroutineScope] that uses [Dispatchers.Main] by default, and that will be cancelled as
 * soon as this [Lifecycle] [currentState][Lifecycle.getCurrentState] is no longer
 * [at least][Lifecycle.State.isAtLeast] the passed [activeWhile] state.
 *
 * **Beware**: if the current state is lower than the passed [activeWhile] state, you'll get an
 * already cancelled scope.
 */
fun Lifecycle.createScope(activeWhile: Lifecycle.State): CoroutineScope {
    return CoroutineScope(createJob(activeWhile) + Dispatchers.Main)
}

/**
 * Creates a [SupervisorJob] that will be cancelled as soon as this [Lifecycle]
 * [currentState][Lifecycle.getCurrentState] is no longer [at least][Lifecycle.State.isAtLeast] the
 * passed [activeWhile] state.
 *
 * **Beware**: if the current state is lower than the passed [activeWhile] state, you'll get an
 * already cancelled job.
 */
fun Lifecycle.createJob(activeWhile: Lifecycle.State = INITIALIZED): Job {
    kotlin.require(activeWhile != Lifecycle.State.DESTROYED) {
        "DESTROYED is a terminal state that is forbidden for createJob(…), to avoid leaks."
    }
    return SupervisorJob().also { job ->
        when (currentState) {
            Lifecycle.State.DESTROYED -> job.cancel()
            else -> GlobalScope.launch(Dispatchers.Main) {
                addObserver(object : GenericLifecycleObserver {
                    override fun onStateChanged(source: LifecycleOwner?, event: Lifecycle.Event) {
                        if (!currentState.isAtLeast(activeWhile)) {
                            removeObserver(this)
                            job.cancel()
                        }
                    }
                })
            }
        }
    }
}
