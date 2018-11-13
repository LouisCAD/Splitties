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
import android.arch.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ConcurrentHashMap

/**
 * Returns a [CoroutineScope] that uses [Dispatchers.Main] by default, and that is cancelled when
 * the [Lifecycle] reaches [Lifecycle.State.DESTROYED] state.
 *
 * Note that this value is cached until the Lifecycle reaches the destroyed state.
 */
val Lifecycle.coroutineScope: CoroutineScope
    get() = cachedLifecycleCoroutineScopes[this] ?: job.let { job ->
        val newScope = CoroutineScope(job + Dispatchers.Main)
        if (job.isActive) {
            cachedLifecycleCoroutineScopes[this] = newScope
            job.invokeOnCompletion { _ -> cachedLifecycleCoroutineScopes -= this }
        }
        newScope
    }

/**
 * Calls [Lifecycle.coroutineScope] for the [Lifecycle] of this [LifecycleOwner].
 *
 * This is an inline property, just there for convenient usage from any [LifecycleOwner],
 * like FragmentActivity, AppCompatActivity, Fragment and LifecycleService.
 */
inline val LifecycleOwner.coroutineScope get() = lifecycle.coroutineScope

/**
 * Returns a [SupervisorJob] that will be cancelled as soon as the [Lifecycle] reaches
 * [Lifecycle.State.DESTROYED] state.
 *
 * Note that this value is cached until the Lifecycle reaches the destroyed state.
 *
 * You can use this job for custom [CoroutineScope]s, or as a parent [Job].
 */
val Lifecycle.job: Job
    get() = cachedLifecycleJobs[this] ?: createJob().also {
        if (it.isActive) {
            cachedLifecycleJobs[this] = it
            it.invokeOnCompletion { _ -> cachedLifecycleJobs -= this }
        }
    }

private val cachedLifecycleJobs = ConcurrentHashMap<Lifecycle, Job>()
private val cachedLifecycleCoroutineScopes = ConcurrentHashMap<Lifecycle, CoroutineScope>()
