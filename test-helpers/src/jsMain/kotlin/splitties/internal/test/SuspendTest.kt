/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

@OptIn(DelicateCoroutinesApi::class)
actual fun runTest(
    alsoRunInNativeWorker: Boolean,
    skipIfRoboelectric: Boolean,
    block: suspend CoroutineScope.() -> Unit
): dynamic = GlobalScope.promise {
    block()
}
