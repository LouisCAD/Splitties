/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

@ExperimentalSplittiesApi
@PotentialFutureAndroidXLifecycleKtxApi
class LifecycleTest {

    @UseExperimental(ObsoleteCoroutinesApi::class)
    private val mainDispatcherSurrogate = newSingleThreadContext("main thread surrogate")

    @BeforeTest
    @UseExperimental(ExperimentalCoroutinesApi::class)
    fun setUp() {
        Dispatchers.setMain(mainDispatcherSurrogate)
    }

    @Test
    fun test_lifecycle_default_scope_uses_default_job() = runBlocking {
        val lifecycle = TestLifecycleOwner().lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        val lifecycleJob = lifecycle.job
        val lifecycleScopeJob = lifecycle.coroutineScope.coroutineContext[Job]
        assertSame(lifecycleJob, lifecycleScopeJob)
        lifecycle.markState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun test_lifecycle_job_is_cached() = runBlocking {
        val lifecycle = TestLifecycleOwner().lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        assertSame(lifecycle.job, lifecycle.job)
        lifecycle.markState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun test_lifecycle_on_destroy_cancels_job() = runBlocking {
        val lifecycle = TestLifecycleOwner().lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        val job = lifecycle.job
        assertTrue(job.isActive)
        assertFalse(job.isCancelled)
        lifecycle.markState(Lifecycle.State.STARTED)
        lifecycle.markState(Lifecycle.State.RESUMED)
        assertTrue(job.isActive)
        assertFalse(job.isCancelled)
        lifecycle.markState(Lifecycle.State.DESTROYED)
        suspendCoroutine<Unit> { c ->
            job.invokeOnCompletion { c.resume(Unit) }
        }
        assertTrue(job.isCancelled)
        assertFalse(job.isActive)
    }

    @Test
    fun test_already_destroyed_lifecycle_makes_cancelled_job() = runBlocking {
        val lifecycle = TestLifecycleOwner().lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        lifecycle.markState(Lifecycle.State.DESTROYED)
        val job = lifecycle.job
        assertFalse(job.isActive)
        assertTrue(job.isCancelled)
    }

    @Test
    fun test_lifecycle_owner_scope_is_lifecycle_scope() = runBlocking {
        val lifecycleOwner = TestLifecycleOwner()
        val lifecycle = lifecycleOwner.lifecycle
        assertSame(lifecycle.coroutineScope, lifecycleOwner.coroutineScope)
    }

    @Test
    fun test_scope_is_not_active_after_destroy() = runBlocking {
        var count = 0
        val lifecycle = TestLifecycleOwner().lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        lifecycle.markState(Lifecycle.State.STARTED)
        val scope = lifecycle.createScope(Lifecycle.State.STARTED)
        assertEquals(1, ++count)
        val testJob = scope.launch {
            try {
                assertEquals(2, ++count)
                delay(Long.MAX_VALUE)
                assertTrue(false, "Should never be reached")
            } catch (e: CancellationException) {
                assertEquals(3, ++count)
                throw e
            } finally {
                assertEquals(4, ++count)
            }
        }
        lifecycle.markState(Lifecycle.State.CREATED)
        testJob.join()
        assertEquals(5, ++count)
        assertFalse(scope.isActive)
        lifecycle.markState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun test_observer_is_called_after_destroy() = runBlocking {
        val lifecycle = TestLifecycleOwner().lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        val activeWhile = Lifecycle.State.INITIALIZED
        val job = with(lifecycle) {
            Job().also { job ->
                addObserver(object : GenericLifecycleObserver {
                    override fun onStateChanged(source: LifecycleOwner?, event: Lifecycle.Event) {
                        if (currentState < activeWhile) {
                            removeObserver(this)
                            job.cancel()
                        }
                    }
                })
            }
        }
        assertTrue(job.isActive)
        assertFalse(job.isCancelled)
        lifecycle.markState(Lifecycle.State.DESTROYED)
        job.join()
        assertFalse(job.isActive)
        assertTrue(job.isCancelled)
    }

    @AfterTest
    fun closeTestMainDispatcher() {
        mainDispatcherSurrogate.close()
    }

    private class TestLifecycleOwner : LifecycleOwner {
        private val lifecycle = LifecycleRegistry(this)
        override fun getLifecycle() = lifecycle
    }
}
