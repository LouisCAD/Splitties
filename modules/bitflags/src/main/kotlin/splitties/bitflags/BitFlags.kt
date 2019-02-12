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

import androidx.annotation.CheckResult
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

@CheckResult
inline fun Long.hasFlag(flag: Long): Boolean = flag and this == flag

@CheckResult
inline fun Long.withFlag(flag: Long): Long = this or flag

@CheckResult
inline fun Long.minusFlag(flag: Long): Long = this and flag.inv()

@CheckResult
inline fun Int.hasFlag(flag: Int): Boolean = flag and this == flag

@CheckResult
inline fun Int.withFlag(flag: Int): Int = this or flag

@CheckResult
inline fun Int.minusFlag(flag: Int): Int = this and flag.inv()

@CheckResult
inline fun Short.hasFlag(flag: Short): Boolean = flag and this == flag

@CheckResult
inline fun Short.withFlag(flag: Short): Short = this or flag

@CheckResult
inline fun Short.minusFlag(flag: Short): Short = this and flag.inv()

@CheckResult
inline fun Byte.hasFlag(flag: Byte): Boolean = flag and this == flag

@CheckResult
inline fun Byte.withFlag(flag: Byte): Byte = this or flag

@CheckResult
inline fun Byte.minusFlag(flag: Byte): Byte = this and flag.inv()

@CheckResult
@ExperimentalUnsignedTypes
inline fun ULong.hasFlag(flag: ULong): Boolean = flag and this == flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun ULong.withFlag(flag: ULong): ULong = this or flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun ULong.minusFlag(flag: ULong): ULong = this and flag.inv()

@CheckResult
@ExperimentalUnsignedTypes
inline fun UInt.hasFlag(flag: UInt): Boolean = flag and this == flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun UInt.withFlag(flag: UInt): UInt = this or flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun UInt.minusFlag(flag: UInt): UInt = this and flag.inv()

@CheckResult
@ExperimentalUnsignedTypes
inline fun UShort.hasFlag(flag: UShort): Boolean = flag and this == flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun UShort.withFlag(flag: UShort): UShort = this or flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun UShort.minusFlag(flag: UShort): UShort = this and flag.inv()

@CheckResult
@ExperimentalUnsignedTypes
inline fun UByte.hasFlag(flag: UByte): Boolean = flag and this == flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun UByte.withFlag(flag: UByte): UByte = this or flag

@CheckResult
@ExperimentalUnsignedTypes
inline fun UByte.minusFlag(flag: UByte): UByte = this and flag.inv()
