/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import splitties.experimental.ExperimentalSplittiesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@UseExperimental(ExperimentalSplittiesApi::class)
class PreferencesTests {
    private class DefaultPrefs : DefaultPreferences() {
        var value1 by IntPref(key = "value1", defaultValue = 0)
    }

    private val defaultPrefs = DefaultPrefs()

    @Test
    fun testPutValueAndGetValue() {
        defaultPrefs.value1 = 1
        assertEquals(expected = 1, actual = defaultPrefs.value1)
    }
}
