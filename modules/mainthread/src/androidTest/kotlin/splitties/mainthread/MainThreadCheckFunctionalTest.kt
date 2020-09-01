/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainthread

import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
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
