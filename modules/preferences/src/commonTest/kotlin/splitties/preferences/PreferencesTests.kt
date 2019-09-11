/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.yield
import splitties.experimental.ExperimentalSplittiesApi
import splitties.internal.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@UseExperimental(
    ExperimentalSplittiesApi::class,
    ExperimentalTime::class,
    ExperimentalCoroutinesApi::class
)
class PreferencesTests {

    private open class DefaultPrefs : DefaultPreferences() {

        val someBoolField = boolPref(key = "someBool", defaultValue = true)
        var someBool by someBoolField

        val someIntField = intPref(key = "someInt", defaultValue = 5)
        var someInt by someIntField

        val someFloatField = floatPref(key = "someFloat", defaultValue = 5f)
        var someFloat by someFloatField

        val someLongField = longPref(key = "someLong", defaultValue = 5L)
        var someLong by someLongField

        val someStringField = stringPref(key = "someString", defaultValue = "")
        var someString by someStringField

        val someStringOrNullField = stringOrNullPref(key = "someStringOrNull", defaultValue = null)
        var someStringOrNull by someStringOrNullField

        val someStringSetField = stringSetPref(key = "someStringSet")
        var someStringSet by someStringSetField

        val someStringSetOrNullField = stringSetOrNullPref(key = "someStringSetOrNull")
        var someStringSetOrNull by someStringSetOrNullField
    }

    private val defaultPrefs = DefaultPrefs()

    @Test
    fun default_values_are_correct() {
        with(defaultPrefs) {
            assertEquals(someBoolField.defaultValue, someBool)
            assertEquals(someIntField.defaultValue, someInt)
            assertEquals(someFloatField.defaultValue, someFloat)
            assertEquals(someLongField.defaultValue, someLong)
            assertEquals(someStringField.defaultValue, someString)
            assertEquals(someStringOrNullField.defaultValue, someStringOrNull)
            assertEquals(someStringSetField.defaultValue, someStringSet)
            assertEquals(someStringSetOrNullField.defaultValue, someStringSetOrNull)
        }
    }

    @Test
    fun set_value_and_get_it_back() {
        testSetAndGetValue(defaultPrefs, DefaultPrefs())
    }

    private object TopLevelObjectDefaultPrefs : DefaultPrefs()

    @Test
    fun test_object() {
        testSetAndGetValue(TopLevelObjectDefaultPrefs, DefaultPrefs())
    }

    private fun testSetAndGetValue(prefs: DefaultPrefs, secondPrefsInstance: DefaultPrefs) {
        booleanArrayOf(true, false).forEach { testedValue ->
            prefs.someBool = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someBool)
            assertEquals(expected = testedValue, actual = prefs.someBool)
        }
        intArrayOf(2, 1, 0, -1, Int.MAX_VALUE, Int.MIN_VALUE).forEach { testedValue ->
            prefs.someInt = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someInt)
            assertEquals(expected = testedValue, actual = prefs.someInt)
        }
        floatArrayOf(
            0f,
            -1f,
            1f,
            Float.NaN,
            Float.NEGATIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.MIN_VALUE,
            Float.MAX_VALUE
        ).forEach { testedValue ->
            prefs.someFloat = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someFloat)
            assertEquals(expected = testedValue, actual = prefs.someFloat)
        }
        longArrayOf(2, 1, 0, -1, Long.MAX_VALUE, Long.MIN_VALUE).forEach { testedValue ->
            prefs.someLong = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someLong)
            assertEquals(expected = testedValue, actual = prefs.someLong)
        }
        val stringTestValues = setOf("Hello World!", "<".repeat(1_000_000), "", " ", "\\")
        stringTestValues.forEach { testedValue ->
            prefs.someString = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someString)
            assertEquals(expected = testedValue, actual = prefs.someString)
            prefs.someStringOrNull = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someStringOrNull)
            assertEquals(expected = testedValue, actual = prefs.someStringOrNull)
        }
        prefs.someStringOrNull = null
        assertEquals(expected = null, actual = secondPrefsInstance.someStringOrNull)
        assertEquals(expected = null, actual = prefs.someStringOrNull)
        stringTestValues.let { testedValue ->
            prefs.someStringSet = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someStringSet)
            assertEquals(expected = testedValue, actual = prefs.someStringSet)
            prefs.someStringSetOrNull = testedValue
            assertEquals(expected = testedValue, actual = secondPrefsInstance.someStringSetOrNull)
            assertEquals(expected = testedValue, actual = prefs.someStringSetOrNull)
        }
        prefs.someStringSetOrNull = null
        assertEquals(expected = null, actual = secondPrefsInstance.someStringSetOrNull)
        assertEquals(expected = null, actual = prefs.someStringSetOrNull)
    }

    @Test
    fun test_changesFlow() = runTest(timeout = 5.seconds) {
        val flow = defaultPrefs.someBoolField.changesFlow().buffer(Channel.UNLIMITED)
        repeat(4) {
            var count = 0
            @UseExperimental(FlowPreview::class)
            flow.onEach { count++ }.produceIn(this).consume {
                awaitCallbackFlowActivation()
                defaultPrefs.someBool = defaultPrefs.someBool.not()
                receive()
                defaultPrefs.someBool = defaultPrefs.someBool.not()
                receive()
                assertEquals(2, count, message = "Iteration: $it")
            }
        }
    }

    @Test
    fun test_valuesFlow() = runTest(timeout = 5.seconds) {
        val startValue = 7
        defaultPrefs.someInt = startValue
        val flow = defaultPrefs.someIntField.valueFlow().buffer(Channel.UNLIMITED)
        @UseExperimental(FlowPreview::class)
        flow.produceIn(this).consume {
            awaitCallbackFlowActivation()
            assertEquals(startValue, receive())
            arrayOf(0, 6, 7, 1, 3).forEach { testValue ->
                defaultPrefs.someInt = testValue
                assertEquals(testValue, receive())
            }
        }
    }

    private suspend fun awaitCallbackFlowActivation() {
        repeat(2) { yield() } // It takes two dispatch for the listener registration to be executed.
    }

    @BeforeTest
    @AfterTest
    fun clearDefaultPrefs() {
        defaultPrefs.prefs.edit().clear().commit()
    }
}
