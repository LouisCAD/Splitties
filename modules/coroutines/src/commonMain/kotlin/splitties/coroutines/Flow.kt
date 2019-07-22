/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * **Will finally be integrated into kotlinx.coroutines.**
 * See proposal in [this issue](https://github.com/Kotlin/kotlinx.coroutines/issues/1269).
 *
 * Passes each element to [action], cancelling any previous [action].
 *
 * You can choose to skip equal values by setting the [skipEquals] parameter to `true`. This is
 * slightly more efficient than applying the [distinctUntilChanged] operator before as no
 * intermediate flow is created.
 */
@ObsoleteCoroutinesApi // Will finally be integrated into kotlinx.coroutines
@ExperimentalCoroutinesApi
suspend fun <E> Flow<E>.collectLatest(
    context: CoroutineContext = EmptyCoroutineContext,
    skipEquals: Boolean = false,
    action: suspend CoroutineScope.(E) -> Unit
): Unit = coroutineScope {
    var job: Job? = null
    var previousValue: E? = null // Null is safe as first value because at first, job is null.
    collect { newValue ->
        job?.let {
            // No skipEquals on first value since there's no job yet.
            if (skipEquals && previousValue == newValue) return@collect else it.cancelAndJoin()
        }
        previousValue = newValue
        job = launch(context) {
            action(newValue)
        }
    }
}
