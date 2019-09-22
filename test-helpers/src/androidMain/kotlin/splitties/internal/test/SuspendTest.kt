/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue

actual fun runTest(
    alsoRunInNativeWorker: Boolean,
    skipIfRoboelectric: Boolean,
    block: suspend CoroutineScope.() -> Unit
) {
    if (skipIfRoboelectric) {
        val roboelectric = "robolectric"
        assumeTrue(
            "Running with Roboelectric, test skipped",
            Build.DEVICE != roboelectric && Build.PRODUCT != roboelectric
        )
    }
    runBlocking { block() }
}
