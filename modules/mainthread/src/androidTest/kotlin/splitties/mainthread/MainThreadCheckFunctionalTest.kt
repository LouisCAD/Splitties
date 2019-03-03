/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainthread

import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class MainThreadCheckFunctionalTest {

    @Test
    fun testMainThreadCheck() {
        runBlocking(Dispatchers.Main) {
            assertTrue(isMainThread)
            checkMainThread()
            assertFailsWith<IllegalStateException> { checkNotMainThread() }
        }
        assertFailsWith<IllegalStateException> { checkMainThread() }
        checkNotMainThread()
        assertFalse(isMainThread)
    }

    @Test
    fun testMainThreadAndLooperIdentity() {
        assertSame(mainLooper, Looper.getMainLooper())
        assertSame(mainThread, Looper.getMainLooper().thread)
    }
}
