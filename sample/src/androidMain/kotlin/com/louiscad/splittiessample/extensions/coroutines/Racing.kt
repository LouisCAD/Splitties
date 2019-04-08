/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample.extensions.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.select

suspend fun <T> raceOf(vararg racers: suspend CoroutineScope.() -> T): T = coroutineScope {
    select<T> {
        @UseExperimental(ExperimentalCoroutinesApi::class)
        val racersAsyncList = racers.map { async(start = CoroutineStart.UNDISPATCHED, block = it) }
        racersAsyncList.forEach { racer ->
            racer.onAwait { resultOfWinner ->
                racersAsyncList.forEach { deferred -> deferred.cancel() }
                return@onAwait resultOfWinner
            }
        }
    }
}
