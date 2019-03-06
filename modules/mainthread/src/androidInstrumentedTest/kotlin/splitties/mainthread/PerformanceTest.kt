/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainthread

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.LIBRARY_IMPL
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.LOCAL_CACHED_THREAD_BY_ID
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.LOCAL_CACHED_THREAD_ID
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.LOCAL_CACHED_THREAD_REF
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.LOOPER
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.LOOPER_THREAD_REF
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.THREAD_EQUALS
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.THREAD_ID
import kotlin.system.measureNanoTime
import kotlin.test.Test
import kotlin.test.assertTrue

class PerformanceTest {

    private enum class MainThreadCheckTechnique {
        LOOPER,
        THREAD_ID,
        THREAD_EQUALS,
        LOCAL_CACHED_THREAD_BY_ID,
        LOCAL_CACHED_THREAD_ID,
        LOOPER_THREAD_REF,
        LOCAL_CACHED_THREAD_REF,
        LIBRARY_IMPL
    }

    @Test
    fun compareMainThreadChecks(): Unit = runBlocking(Dispatchers.Main) {
        val techniqueList = MainThreadCheckTechnique.values().asList()
        val results = mutableMapOf<MainThreadCheckTechnique, Long>().also { resultsMap ->
            val reverseList = techniqueList.reversed()
            repeat(100) { i ->
                val runList = (if (i % 2 == 0) techniqueList else reverseList)
                runList.onEach { technique ->
                    val result = runBenchmark(technique)
                    resultsMap[technique] = resultsMap.getOrElse(technique) { 0 } + result
                }
            }
        }.toList().sortedBy { (_, result) -> result }
        val techniqueNameLength = MainThreadCheckTechnique.values().maxBy {
            it.name.length
        }!!.name.length
        val tag = "MainThreadPerformanceTest"
        Log.i(tag, "Benchmark results below")
        results.forEach { (technique, result) ->
            val techName = technique.name.replace('_', ' ').toLowerCase().capitalize()
                .padEnd(techniqueNameLength)
            Log.d(tag, "$techName duration (in µs): $result")
        }
        assertTrue("Library implementation should be the fastest technique! Check logs.") {
            val (technique, _) = results.minBy { (_, result) -> result }!!
            technique == LIBRARY_IMPL
        }
    }.let { Unit }

    private fun runBenchmark(technique: MainThreadCheckTechnique): Long {
        val mainThread = mainLooper.thread
        val mainThreadId = mainThread.id
        return when (technique) {
            LIBRARY_IMPL -> benchmark { isMainThread }
            LOOPER -> benchmark { mainLooper == Looper.myLooper() }
            THREAD_ID -> benchmark { mainLooper.thread.id == Thread.currentThread().id }
            THREAD_EQUALS -> benchmark { mainLooper.thread == Thread.currentThread() }
            LOCAL_CACHED_THREAD_BY_ID -> benchmark { mainThread.id == Thread.currentThread().id }
            LOCAL_CACHED_THREAD_ID -> benchmark { mainThreadId == Thread.currentThread().id }
            LOOPER_THREAD_REF -> benchmark { mainLooper.thread === Thread.currentThread() }
            LOCAL_CACHED_THREAD_REF -> benchmark {
                mainThread == Thread.currentThread() // This seems to be the fastest technique.
            }
        }
    }

    private inline fun benchmark(runs: Int = 10_000, f: () -> Boolean): Long = measureNanoTime {
        repeat(runs) { check(f()) }
    } / 1000
}
