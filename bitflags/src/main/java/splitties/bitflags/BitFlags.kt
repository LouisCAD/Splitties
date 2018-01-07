/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
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

@file:Suppress("NOTHING_TO_INLINE")

package splitties.bitflags

import android.support.annotation.CheckResult
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

@CheckResult inline fun Long.hasFlag(flag: Long) = flag and this == flag
@CheckResult inline fun Long.withFlag(flag: Long) = this or flag
@CheckResult inline fun Long.minusFlag(flag: Long) = this and flag.inv()

@CheckResult inline fun Int.hasFlag(flag: Int) = flag and this == flag
@CheckResult inline fun Int.withFlag(flag: Int) = this or flag
@CheckResult inline fun Int.minusFlag(flag: Int) = this and flag.inv()

@CheckResult inline fun Short.hasFlag(flag: Short) = flag and this == flag
@CheckResult inline fun Short.withFlag(flag: Short) = this or flag
@CheckResult inline fun Short.minusFlag(flag: Short) = this and flag.inv()

@CheckResult inline fun Byte.hasFlag(flag: Byte) = flag and this == flag
@CheckResult inline fun Byte.withFlag(flag: Byte) = this or flag
@CheckResult inline fun Byte.minusFlag(flag: Byte) = this and flag.inv()
