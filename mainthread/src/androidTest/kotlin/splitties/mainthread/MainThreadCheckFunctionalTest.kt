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
