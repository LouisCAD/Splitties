/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("DEPRECATION")

package splitties.lifecycle.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.mainthread.isMainThread
import java.util.concurrent.ConcurrentHashMap

/**
 * Returns a [CoroutineScope] that uses [Dispatchers.MainAndroid] by default, and that is cancelled when
 * the [Lifecycle] reaches [Lifecycle.State.DESTROYED] state.
 *
 * Note that this value is cached until the Lifecycle reaches the destroyed state.
 */
@Deprecated(
    message = defaultDeprecationMessage,
    replaceWith = ReplaceWith("coroutineScope", "androidx.lifecycle.coroutineScope")
)
@OptIn(MainDispatcherPerformanceIssueWorkaround::class)
val Lifecycle.coroutineScope: CoroutineScope
    get() = cachedLifecycleCoroutineScopes.let { cache ->
        cache[this] ?: job.let { job ->
            val newScope = CoroutineScope(job + Dispatchers.MainAndroid)
            if (job.isActive) {
                cache[this] = newScope
                job.invokeOnCompletion { _ -> cache -= this }
            }
            newScope
        }
    }

/**
 * Calls [Lifecycle.coroutineScope] for the [Lifecycle] of this [LifecycleOwner].
 *
 * This is an inline property, just there for convenient usage from any [LifecycleOwner],
 * like FragmentActivity, AppCompatActivity, Fragment and LifecycleService.
 */
@Deprecated(
    message = defaultDeprecationMessage,
    replaceWith = ReplaceWith("lifecycleScope", "androidx.lifecycle.lifecycleScope")
)
inline val LifecycleOwner.lifecycleScope
    get() = lifecycle.coroutineScope

/**
 * Calls [Lifecycle.coroutineScope] for the [Lifecycle] of this [LifecycleOwner].
 *
 * This is an inline property, just there for convenient usage from any [LifecycleOwner],
 * like FragmentActivity, AppCompatActivity, Fragment and LifecycleService.
 */
@Deprecated(
    message = defaultDeprecationMessage,
    replaceWith = ReplaceWith("lifecycleScope", "androidx.lifecycle.lifecycleScope"),
    level = DeprecationLevel.ERROR
)
inline val LifecycleOwner.coroutineScope
    get() = lifecycleScope

/**
 * Returns a [SupervisorJob] that will be cancelled as soon as the [Lifecycle] reaches
 * [Lifecycle.State.DESTROYED] state.
 *
 * Note that this value is cached until the Lifecycle reaches the destroyed state.
 *
 * You can use this job for custom [CoroutineScope]s, or as a parent [Job].
 */
@Deprecated(
    message = defaultDeprecationMessage,
    replaceWith = ReplaceWith(
        "coroutineScope.coroutineContext[Job]!!",
        "androidx.lifecycle.coroutineScope",
        "kotlinx.coroutines.Job"
    )
)
val Lifecycle.job: Job
    get() = cachedLifecycleJobs.let { cache ->
        @OptIn(ExperimentalSplittiesApi::class)
        cache[this] ?: createJob().also {
            if (it.isActive) {
                cache[this] = it
                it.invokeOnCompletion { _ -> cache -= this }
            }
        }
    }

private inline val cachedLifecycleJobs: MutableMap<Lifecycle, Job>
    get() = try {
        if (isMainThread) mainThreadJobs else anyThreadJobs
    } catch (cantCheckMainThreadInTestsError: ExceptionInInitializerError) {
        anyThreadJobs
    } catch (cantCheckMainThreadInTestsError: NoClassDefFoundError) {
        anyThreadJobs
    }
private inline val cachedLifecycleCoroutineScopes: MutableMap<Lifecycle, CoroutineScope>
    get() = try {
        if (isMainThread) mainThreadScopes else anyThreadScopes
    } catch (cantCheckMainThreadInTestsError: ExceptionInInitializerError) {
        anyThreadScopes
    } catch (cantCheckMainThreadInTestsError: NoClassDefFoundError) {
        anyThreadScopes
    }

private val mainThreadJobs = mutableMapOf<Lifecycle, Job>()
private val mainThreadScopes = mutableMapOf<Lifecycle, CoroutineScope>()

private val anyThreadJobs = ConcurrentHashMap<Lifecycle, Job>()
private val anyThreadScopes = ConcurrentHashMap<Lifecycle, CoroutineScope>()
