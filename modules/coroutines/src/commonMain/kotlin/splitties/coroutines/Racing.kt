/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import splitties.experimental.ExperimentalSplittiesApi
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED as Undispatched

/**
 * Pass at least one racer to use [raceOf], or use [race] if the racers need to be launched
 * dynamically.
 */
@Suppress("DeprecatedCallableAddReplaceWith", "RedundantSuspendModifier")
@Deprecated("A race needs racers.", level = DeprecationLevel.ERROR) // FOOL GUARD, DO NOT REMOVE
suspend fun <T> raceOf(): T = throw UnsupportedOperationException("A race needs racers.")

/**
 * Races all the [racers] concurrently. Once the winner completes, all other racers are cancelled,
 * then the value of the winner is returned.
 *
 * Use [race] if the racers need to be launched dynamically.
 */
@ExperimentalSplittiesApi
suspend fun <T> raceOf(vararg racers: suspend CoroutineScope.() -> T): T {
    require(racers.isNotEmpty()) { "A race needs racers." }
    return coroutineScope {
        val racersParent = Job(parent = coroutineContext[Job])
        @Suppress("RemoveExplicitTypeArguments")
        select<T> {
            racers.forEach { racer ->
                async(
                    context = racersParent,
                    start = Undispatched,
                    block = racer
                ).onAwait { resultOfWinner: T ->
                    racersParent.cancel()
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
@ExperimentalSplittiesApi
interface RacingScope<in T> : CoroutineScope {
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
@ExperimentalSplittiesApi
inline fun <T> RacingScope<T>.launchRacer(noinline block: suspend CoroutineScope.() -> T) {
    @Suppress("DEPRECATION")
    launchRacerInternal(block)
}

/**
 * Starts a [RacingScope] with the suspending [builder] lambda in which you can call [launchRacer]
 * each time you want to launch a racer coroutine. Once a racer completes, the [builder] and all
 * racers are cancelled, then the value of the winning racer is returned.
 *
 * For races where the number of racers is static, you can use the slightly more efficient [raceOf]
 * function and directly pass the cancellable lambdas you want to race concurrently.
 */
@ExperimentalSplittiesApi
@OptIn(ExperimentalTypeInference::class)
suspend fun <T> race(
    @BuilderInference
    builder: suspend RacingScope<T>.() -> Unit
): T = coroutineScope {
    @Suppress("RemoveExplicitTypeArguments")
    select<T> {
        val builderJob = Job(parent = coroutineContext[Job])

        val racingScope = object : RacingScope<T>, CoroutineScope by this@coroutineScope {

            var raceWon = false

            @Suppress("OverridingDeprecatedMember", "OVERRIDE_DEPRECATION")
            override fun launchRacerInternal(block: suspend CoroutineScope.() -> T) {
                if (raceWon) return // A racer already completed.
                async(
                    context = builderJob,
                    start = Undispatched,
                    block = block
                ).onAwait { resultOfWinner: T ->
                    raceWon = true
                    builderJob.cancel()
                    return@onAwait resultOfWinner
                }
            }
        }
        launch(builderJob, start = Undispatched) {
            racingScope.builder()
        }
    }
}
