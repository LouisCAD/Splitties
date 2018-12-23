/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package splitties.mainthread

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import splitties.mainthread.PerformanceTest.MainThreadCheckTechnique.*
import kotlin.system.measureNanoTime
import kotlin.test.assertTrue
import kotlin.test.Test

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
            val (technique, _) = results.minBy { (_, result) -> result}!!
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
