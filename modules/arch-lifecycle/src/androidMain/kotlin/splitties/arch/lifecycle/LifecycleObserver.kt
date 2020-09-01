/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import splitties.experimental.ExperimentalSplittiesApi

/**
 * A LifecycleObserver interface for Kotlin where you can implement only what you need.
 * Make sure you import the right one when implementing it.
 */
//TODO: Move the artifact so it depends only on lifecycle-common.
@ExperimentalSplittiesApi
interface LifecycleObserver : LifecycleEventObserver {

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
            ON_ANY -> error("ON_ANY must not be sent by anybody")
        }
    }
}
