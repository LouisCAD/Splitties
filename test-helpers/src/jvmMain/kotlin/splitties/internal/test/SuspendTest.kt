/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun runTest(
    alsoRunInNativeWorker: Boolean,
    skipIfRoboelectric: Boolean,
    block: suspend CoroutineScope.() -> Unit
) = runBlocking { block() }
