/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.internal.test

import kotlin.reflect.KClass

@UseExperimental(ExperimentalMultiplatform::class)
@OptionalExpectation
expect annotation class RunWith(val value: KClass<out Runner>)


expect abstract class Runner

