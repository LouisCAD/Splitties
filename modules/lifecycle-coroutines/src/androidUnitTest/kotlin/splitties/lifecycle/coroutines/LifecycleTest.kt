/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
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

@Suppress("DEPRECATION")
@ExperimentalSplittiesApi
class LifecycleTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val mainDispatcherSurrogate = newSingleThreadContext("main thread surrogate")

    @BeforeTest
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setUp() {
        Dispatchers.setMain(mainDispatcherSurrogate)
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
                addObserver(object : LifecycleEventObserver {
                    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
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
