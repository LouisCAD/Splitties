/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import splitties.preferences.no_cache_prefs.NoCachePreferences
import kotlin.system.measureTimeMillis

/**
 * Tests implementation with cache version no cache versus vanilla approach.
 */
@RunWith(AndroidJUnit4::class)
class PerformanceTests {

    companion object {
        private lateinit var appContext: Context
        private const val prefsFileName = "testPrefs"
        private const val testKey = "test"
        private const val testDefaultValue = ""
        private const val testString = "Hi, I'm a half-random string used to test preferences!"
        private const val TAG = "cachedPrefsPerfsTest"
    }

    @Test
    @Throws(Exception::class)
    fun testCachedPrefFieldBenefit() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testCount = 1000000
        val prefs = appContext.getSharedPreferences(
            prefsFileName, Context.MODE_PRIVATE)
        assert(prefs.edit().putString(
            testKey,
            testString
        ).commit())
        val cacheT = measureTimeMillis {
            repeat(testCount) {
                val discardMe =
                    CacheTestPrefs.testStringField
            }
        }
        val noCacheT = measureTimeMillis {
            repeat(testCount) {
                val discardMe =
                    NoCacheTestPrefs.testStringField
            }
        }
        val vanilla = measureTimeMillis {
            repeat(testCount) {
                val discardMe = prefs.getString(
                    testKey,
                    testDefaultValue
                )
            }
        }
        Log.d(TAG, "with cache   : $cacheT")
        Log.d(TAG, "without cache: $noCacheT")
        Log.d(TAG, "vanilla   : $vanilla")
    }

    object CacheTestPrefs : Preferences(prefsFileName) {
        var testStringField by StringPref(
            testKey,
            testDefaultValue
        )
    }

    object NoCacheTestPrefs : NoCachePreferences(
        appContext,
        prefsFileName
    ) {
        var testStringField by PStringPref(
            testKey,
            testDefaultValue
        )
    }
}
