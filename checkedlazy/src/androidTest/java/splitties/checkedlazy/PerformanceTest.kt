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

package splitties.checkedlazy

import android.os.Handler
import android.os.Looper
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import splitties.uithread.mainLooper
import timber.log.Timber
import kotlin.system.measureNanoTime

/**
 * Instrumentation test, which will execute on an Android device.
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class PerformanceTest {

    @Before
    fun init() {
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun compareMainThreadChecks() {
        Handler(mainLooper).post {
            Timber.d("looper duration:              ${benchmark {
                check(Looper.myLooper() == mainLooper)
            }}")

            Timber.d("thread id duration:           ${benchmark {
                check(Thread.currentThread().id == mainLooper.thread.id)
            }}")

            Timber.d("thread equals duration:       ${benchmark {
                check(mainLooper.thread == Thread.currentThread())
            }}")

            val mainThread = mainLooper.thread

            Timber.d("cached thread id duration:    ${benchmark {
                check(Thread.currentThread().id == mainThread.id)
            }}")

            val mainThreadId = mainLooper.thread.id

            Timber.d("thread cached id duration:    ${benchmark {
                check(Thread.currentThread().id == mainThreadId)
            }}")

            Timber.d("thread ref duration:          ${benchmark {
                check(Thread.currentThread() === mainLooper.thread)
            }}")

            Timber.d("cached thread equals duration:${benchmark {
                check(mainThread == Thread.currentThread())
            }}")

            Timber.d("cached thread ref duration:   ${benchmark {
                check(Thread.currentThread() === mainThread)
            }}") // This seems to be the fastest way to check ui thread.
        }
    }

    private inline fun benchmark(f: () -> Unit) = measureNanoTime {
        repeat(10000) {
            f()
        }
    }
}
