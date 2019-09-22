/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

actual fun runTest(
    alsoRunInNativeWorker: Boolean,
    skipIfRoboelectric: Boolean,
    block: suspend CoroutineScope.() -> Unit
) {
    runBlocking {
        block()
        if (alsoRunInNativeWorker) Worker.start().execute(TransferMode.SAFE, { block.freeze() }) {
            runBlocking { it() }
        }.result
    }
}
