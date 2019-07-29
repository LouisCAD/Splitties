/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.select
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED as Undispatched

@Suppress("DeprecatedCallableAddReplaceWith", "RedundantSuspendModifier")
@Deprecated("A race needs racers.", level = DeprecationLevel.ERROR)
suspend fun <T> raceOf(): T = throw UnsupportedOperationException("A race needs racers.")

suspend fun <T> raceOf(vararg racers: suspend CoroutineScope.() -> T): T {
    require(racers.isNotEmpty()) { "A race needs racers." }
    return coroutineScope {
        select<T> {
            @UseExperimental(ExperimentalCoroutinesApi::class)
            val racersAsyncList = racers.map {
                async(start = Undispatched, block = it)
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

/**
 * A scope meant to be used in [race] lambda receiver.
 *
 * You should not implement this interface yourself.
 */
interface RacingScope<T> {
    @Deprecated(
        message = "Internal API",
        replaceWith = ReplaceWith("launchRacer(block)", "splitties.coroutines.launchRacer")
    )
    fun launchRacerInternal(block: suspend CoroutineScope.() -> T)
}

inline fun <T> RacingScope<T>.launchRacer(noinline block: suspend CoroutineScope.() -> T) {
    @Suppress("DEPRECATION")
    launchRacerInternal(block)
}

@UseExperimental(ExperimentalTypeInference::class)
suspend fun <T> race(@BuilderInference builder: RacingScope<T>.() -> Unit): T = coroutineScope {
    val racersAsyncList = mutableListOf<Deferred<T>>()
    select<T> {
        val racingScope = object : RacingScope<T> {
            @Suppress("OverridingDeprecatedMember")
            override fun launchRacerInternal(block: suspend CoroutineScope.() -> T) {
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
