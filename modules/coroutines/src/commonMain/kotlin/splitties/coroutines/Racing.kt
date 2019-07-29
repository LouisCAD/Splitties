/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import splitties.collections.forEachByIndex
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED as Undispatched

@Suppress("DeprecatedCallableAddReplaceWith", "RedundantSuspendModifier")
@Deprecated("A race needs racers.", level = DeprecationLevel.ERROR)
suspend fun <T> raceOf(): T = throw UnsupportedOperationException("A race needs racers.")

suspend fun <T> raceOf(vararg racers: suspend CoroutineScope.() -> T): T {
    require(racers.isNotEmpty()) { "A race needs racers." }
    return coroutineScope {
        @Suppress("RemoveExplicitTypeArguments")
        select<T> {
            @UseExperimental(ExperimentalCoroutinesApi::class)
            val racersAsyncList = racers.map {
                async(start = Undispatched, block = it)
            }
            racersAsyncList.forEachByIndex { racer: Deferred<T> ->
                racer.onAwait { resultOfWinner: T ->
                    racersAsyncList.forEachByIndex { deferred: Deferred<T> -> deferred.cancel() }
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
interface RacingScope<in T> {
    @Deprecated(
        message = "Internal API",
        replaceWith = ReplaceWith("launchRacer(block)", "splitties.coroutines.launchRacer")
    )
    fun launchRacerInternal(block: suspend CoroutineScope.() -> T)
}

/**
 * Launches a racer in this scope.
 * **Must be cancellable**, it will suspend [race] completion otherwise.
 *
 * Use it inside the lambda passed to the [race] function.
 */
inline fun <T> RacingScope<T>.launchRacer(noinline block: suspend CoroutineScope.() -> T) {
    @Suppress("DEPRECATION")
    launchRacerInternal(block)
}

/**
 * Starts a [RacingScope] with the suspending [builder] lambda in which you can call [launchRacer]
 * each time you want to launch a racer coroutine. Once a racer completes, the [builder] and all
 * racers are cancelled, then the value of the winning racer is returned.
 */
@UseExperimental(ExperimentalTypeInference::class)
suspend fun <T> race(
    @BuilderInference
    builder: suspend RacingScope<T>.() -> Unit
): T = coroutineScope {
    val racersAsyncList = mutableListOf<Deferred<T>>()
    @Suppress("RemoveExplicitTypeArguments")
    select<T> {
        val builderJob = Job(parent = coroutineContext[Job])
        val racingScope = object : RacingScope<T> {
            @Suppress("OverridingDeprecatedMember")
            override fun launchRacerInternal(block: suspend CoroutineScope.() -> T) {
                async(block = block).also { racerAsync ->
                    racersAsyncList += racerAsync
                }.onAwait { resultOfWinner: T ->
                    builderJob.cancel()
                    racersAsyncList.forEach { deferred: Deferred<T> ->
                        deferred.cancel()
                    }
                    return@onAwait resultOfWinner
                }
            }
        }
        @UseExperimental(ExperimentalCoroutinesApi::class)
        launch(builderJob, start = Undispatched) {
            racingScope.builder()
        }
    }
}
