/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        val prefs = appContext.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE)
        assert(prefs.edit().putString(testKey, testString).commit())
        val cacheT = measureTimeMillis {
            repeat(testCount) {
                val discardMe = CacheTestPrefs.testStringField
            }
        }
        val noCacheT = measureTimeMillis {
            repeat(testCount) {
                val discardMe = NoCacheTestPrefs.testStringField
            }
        }
        val vanilla = measureTimeMillis {
            repeat(testCount) {
                val discardMe = prefs.getString(testKey, testDefaultValue)
            }
        }
        Log.d(TAG, "with cache   : $cacheT")
        Log.d(TAG, "without cache: $noCacheT")
        Log.d(TAG, "vanilla   : $vanilla")
    }

    object CacheTestPrefs : Preferences(prefsFileName) {
        var testStringField by StringPref(testKey, testDefaultValue)
    }

    object NoCacheTestPrefs : NoCachePreferences(appContext, prefsFileName) {
        var testStringField by PStringPref(testKey, testDefaultValue)
    }
}
