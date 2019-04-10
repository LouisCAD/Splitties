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
import splitties.exceptions.unsupported

@Suppress("DeprecatedCallableAddReplaceWith", "RedundantSuspendModifier")
@Deprecated("A race need racers.", level = DeprecationLevel.ERROR)
suspend fun <T> raceOf(): T = unsupported()

suspend fun <T> raceOf(vararg racers: suspend CoroutineScope.() -> T): T {
    require(racers.isNotEmpty()) { "A race need racers." }
    return coroutineScope {
        select<T> {
            @UseExperimental(ExperimentalCoroutinesApi::class)
            val racersAsyncList = racers.map {
                async(start = CoroutineStart.UNDISPATCHED, block = it)
            }
            racersAsyncList.forEach { racer: Deferred<T> ->
                racer.onAwait { resultOfWinner: T ->
                    racersAsyncList.forEach { deferred: Deferred<T> -> deferred.cancel() }
                    return@onAwait resultOfWinner
                }
            }
        }
    }
}
