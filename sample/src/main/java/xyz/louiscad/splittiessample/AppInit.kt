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

package xyz.louiscad.splittiessample

import android.app.Application
import android.content.ContentProvider
import android.os.Looper
import android.util.Log
import timber.log.Timber

/**
 * Initializes some app wide things (e.g. the logging library Timber).
 * This object needs to be invoked (`AppInit`) in a [ContentProvider] or an [Application].
 */
object AppInit {
    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        compareMainThreadChecks()
    }

    fun compareMainThreadChecks() {
        val iterations = 1000
        val mainLooper = Looper.getMainLooper()

        var before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(Looper.myLooper() == mainLooper)
        }
        var duration = System.nanoTime() - before
        Log.d("uiThread check duration", "looper duration:" + duration / 1000)

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(Thread.currentThread().id == mainLooper.thread.id)
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "thread id duration:" + duration / 1000)

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(mainLooper.thread == Thread.currentThread())
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "thread equals duration:" + duration / 1000)

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(Thread.currentThread() === mainLooper.thread)
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "thread ref duration:" + duration / 1000)

        val mainThread = mainLooper.thread

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(Thread.currentThread().id == mainThread.id)
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "cached thread id duration:" + duration / 1000)

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(mainThread == Thread.currentThread())
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "cached thread equals duration:" + duration / 1000)

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(Thread.currentThread() === mainThread)
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "cached thread ref duration:" + duration / 1000)

        val mainThreadId = mainLooper.thread.id

        before = System.nanoTime()
        for (i in 0..iterations - 1) {
            check(Thread.currentThread().id == mainThreadId)
        }
        duration = System.nanoTime() - before
        Log.d("uiThread check duration", "thread cached id duration:" + duration / 1000)
    }
}