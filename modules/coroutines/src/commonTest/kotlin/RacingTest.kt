package splitties.coroutines

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class RacingTest {

    @Test
    fun testRace() = runTest {
        var ran = false
        var cancelledLateRacer = false
        var cancelledSlowBuilder = false
        val expectedResult = "Yup"
        race {
            launchRacer {
                try {
                    delay(10.milliseconds)
                } catch (e: CancellationException) {
                    cancelledLateRacer = true
                    throw e
                }
                error("Nein")
            }
            launchRacer {
                delay(5.milliseconds)
                ran = true
                expectedResult
            }
            try {
                delay(1.seconds)
            } catch (e: CancellationException) {
                cancelledSlowBuilder = true
                throw e
            }
            launchRacer {
                error("Nope")
            }
        } shouldBe expectedResult
        ran.shouldBeTrue()
        cancelledLateRacer.shouldBeTrue()
        cancelledSlowBuilder.shouldBeTrue()
    }

    @Test
    fun testRaceOf() = runTest {
        val expectedResult = "Yup"
        raceOf({ expectedResult }) shouldBe expectedResult
        raceOf({ expectedResult }, { "Nope" }) shouldBe expectedResult
        raceOf({ yield(); "Nope" }, { expectedResult }) shouldBe expectedResult
        var cancelledSlowRacer = false
        raceOf({
            try {
                delay(2.milliseconds)
            } catch (e: CancellationException) {
                cancelledSlowRacer = true
                throw e
            }
            "Nope"
        }, {
            delay(1.milliseconds)
            expectedResult
        }) shouldBe expectedResult
        cancelledSlowRacer.shouldBeTrue()
    }
}
