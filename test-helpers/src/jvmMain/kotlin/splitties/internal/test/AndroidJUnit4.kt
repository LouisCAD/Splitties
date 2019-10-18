/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier

actual typealias AndroidJUnit4 = JUnit4

class JUnit4(klass: Class<*>) : Runner() {

    private val actualRunner = org.junit.runners.JUnit4(klass)

    override fun run(notifier: RunNotifier?) {
        actualRunner.run(notifier)
    }

    override fun getDescription(): Description = actualRunner.description
}
