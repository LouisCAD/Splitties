/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.select
import splitties.exceptions.unsupported
import kotlin.experimental.ExperimentalTypeInference

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

interface RacingScope<T> {
    fun launchRacer(block: suspend CoroutineScope.() -> T)
}

@UseExperimental(ExperimentalTypeInference::class)
suspend fun <T> race(@BuilderInference builder: RacingScope<T>.() -> Unit): T = coroutineScope {
    val racersAsyncList = mutableListOf<Deferred<T>>()
    select<T> {
        val racingScope = object : RacingScope<T> {
            override fun launchRacer(block: suspend CoroutineScope.() -> T) {
                async(block = block).also { racerAsync ->
                    racersAsyncList += racerAsync
                }.onAwait { resultOfWinner: T ->
                    racersAsyncList.forEach { deferred: Deferred<T> -> deferred.cancel() }
                    return@onAwait resultOfWinner
                }
            }
        }
        racingScope.builder()
    }
}
