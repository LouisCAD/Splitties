/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.coroutines

import splitties.experimental.ExperimentalSplittiesApi
import kotlin.time.*

@ExperimentalTime
@ExperimentalSplittiesApi
suspend inline fun delay(duration: Duration) = kotlinx.coroutines.delay(timeMillis = duration.toLongMilliseconds())
