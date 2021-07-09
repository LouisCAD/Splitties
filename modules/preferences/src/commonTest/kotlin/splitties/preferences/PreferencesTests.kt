/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.yield
import splitties.experimental.ExperimentalSplittiesApi
import splitties.internal.test.AndroidJUnit4
import splitties.internal.test.RunWith
import splitties.internal.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(
    ExperimentalSplittiesApi::class,
    ExperimentalTime::class,
    ExperimentalCoroutinesApi::class
)
@RunWith(AndroidJUnit4::class)
class PreferencesTests {

    @BeforeTest
    @AfterTest
    fun clearDefaultPrefs() {
        defaultPrefs.prefs.edit().clear().commit()
    }

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
        testSetAndGetValue(defaultPrefs)
        testSetAndGetValue(writePrefs = defaultPrefs, readPrefs = DefaultPrefs())
        testSetAndGetValue(writePrefs = DefaultPrefs(), readPrefs = defaultPrefs)
        testSetAndGetValue(TopLevelObjectDefaultPrefs)
        testSetAndGetValue(writePrefs = TopLevelObjectDefaultPrefs, readPrefs = DefaultPrefs())
    }

    @Test
    fun test_changesFlow() = runTest(
        timeout = Duration.seconds(5),
        alsoRunInNativeWorker = true,
        skipIfRoboelectric = true
    ) {
        testChangesFlow(defaultPrefs)
        testChangesFlow(TopLevelObjectDefaultPrefs)
        testChangesFlow(writePrefs = TopLevelObjectDefaultPrefs, readPrefs = defaultPrefs)
        testChangesFlow(
            writePrefs = TopLevelObjectDefaultPrefs,
            readPrefs = defaultPrefs,
            changesPrefs = DefaultPrefs()
        )
    }

    @Test
    fun test_valueFlow() = runTest(
        timeout = Duration.seconds(5),
        alsoRunInNativeWorker = true,
        skipIfRoboelectric = true
    ) {
        testValueFlow(defaultPrefs)
        testValueFlow(TopLevelObjectDefaultPrefs)
        testValueFlow(writePrefs = TopLevelObjectDefaultPrefs, readPrefs = defaultPrefs)
    }

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

    private object TopLevelObjectDefaultPrefs : DefaultPrefs()

    private suspend fun awaitCallbackFlowActivation(extraDispatches: Int = 0) {
        require(extraDispatches >= 0)
        repeat(1 + extraDispatches) {
            yield()
        }
        // It takes one dispatch for the listener registration to be executed.
    }

    private fun testSetAndGetValue(writePrefs: DefaultPrefs, readPrefs: DefaultPrefs = writePrefs) {
        booleanArrayOf(true, false).forEach { testedValue ->
            writePrefs.someBool = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someBool)
        }
        intArrayOf(2, 1, 0, -1, Int.MAX_VALUE, Int.MIN_VALUE).forEach { testedValue ->
            writePrefs.someInt = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someInt)
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
            writePrefs.someFloat = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someFloat)
        }
        longArrayOf(2, 1, 0, -1, Long.MAX_VALUE, Long.MIN_VALUE).forEach { testedValue ->
            writePrefs.someLong = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someLong)
        }
        val stringTestValues = setOf("Hello World!", "<".repeat(1_000_000), "", " ", "\\")
        stringTestValues.forEach { testedValue ->
            writePrefs.someString = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someString)
            writePrefs.someStringOrNull = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someStringOrNull)
        }
        writePrefs.someStringOrNull = null
        assertEquals(expected = null, actual = readPrefs.someStringOrNull)
        stringTestValues.let { testedValue ->
            writePrefs.someStringSet = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someStringSet)
            writePrefs.someStringSetOrNull = testedValue
            assertEquals(expected = testedValue, actual = readPrefs.someStringSetOrNull)
        }
        writePrefs.someStringSetOrNull = null
        assertEquals(expected = null, actual = readPrefs.someStringSetOrNull)
    }

    private suspend fun testChangesFlow(
        writePrefs: DefaultPrefs,
        readPrefs: DefaultPrefs = writePrefs,
        changesPrefs: DefaultPrefs = readPrefs
    ): Unit = coroutineScope {
        val flow = changesPrefs.someBoolField.changesFlow().buffer(Channel.UNLIMITED)
        repeat(4) {
            var count = 0
            @OptIn(FlowPreview::class)
            flow.onEach { count++ }.produceIn(this).consume {
                awaitCallbackFlowActivation(extraDispatches = 1 /* For onEach operator */)
                writePrefs.someBool = readPrefs.someBool.not()
                receive()
                writePrefs.someBool = readPrefs.someBool.not()
                receive()
                assertEquals(2, count, message = "Iteration: $it")
            }
        }
    }

    private suspend fun testValueFlow(
        writePrefs: DefaultPrefs,
        readPrefs: DefaultPrefs = writePrefs
    ): Unit = coroutineScope {
        val startValue = 7
        writePrefs.someInt = startValue
        val flow = readPrefs.someIntField.valueFlow().buffer(Channel.UNLIMITED)
        @OptIn(FlowPreview::class)
        flow.produceIn(this).consume {
            awaitCallbackFlowActivation()
            assertEquals(startValue, receive())
            arrayOf(0, 6, 7, 1, 3).forEach { testValue ->
                writePrefs.someInt = testValue
                assertEquals(testValue, receive())
            }
        }
    }
}
