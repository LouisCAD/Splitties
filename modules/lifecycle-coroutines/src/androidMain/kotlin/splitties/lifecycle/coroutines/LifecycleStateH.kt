/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.*
import kotlin.time.*

@ExperimentalTime
expect fun Lifecycle.isStartedFlow(timeout: Duration): Flow<Boolean>
expect fun Lifecycle.isStartedFlow(): Flow<Boolean>
expect fun Lifecycle.isStateAtLeastFlow(minimalState: Lifecycle.State): Flow<Boolean>

@ExperimentalTime
expect fun Lifecycle.isResumedFlow(timeout: Duration): Flow<Boolean>
expect fun Lifecycle.isResumedFlow(): Flow<Boolean>

expect fun Lifecycle.stateFlow(): Flow<Lifecycle.State>
