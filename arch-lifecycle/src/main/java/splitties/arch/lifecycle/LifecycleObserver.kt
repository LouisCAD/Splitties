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

package splitties.arch.lifecycle

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.*
import android.arch.lifecycle.LifecycleOwner
import splitties.exceptions.illegal
import splitties.experimental.ExperimentalSplittiesApi

/**
 * A LifecycleObserver interface for Kotlin where you can implement only what you need.
 * Make sure you import the right one when implementing it.
 */
@ExperimentalSplittiesApi
interface LifecycleObserver : GenericLifecycleObserver {

    fun onCreate(owner: LifecycleOwner) = Unit
    fun onStart(owner: LifecycleOwner) = Unit
    fun onResume(owner: LifecycleOwner) = Unit
    fun onPause(owner: LifecycleOwner) = Unit
    fun onStop(owner: LifecycleOwner) = Unit
    fun onDestroy(owner: LifecycleOwner) = Unit

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            ON_CREATE -> onCreate(source)
            ON_START -> onStart(source)
            ON_RESUME -> onResume(source)
            ON_PAUSE -> onPause(source)
            ON_STOP -> onStop(source)
            ON_DESTROY -> onDestroy(source)
            ON_ANY -> illegal("ON_ANY must not be sent by anybody")
        }
    }
}
