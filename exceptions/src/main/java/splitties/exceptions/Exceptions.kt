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

package splitties.exceptions

import android.content.Intent

/** Throws an [IllegalStateException] with a message that includes [value]. */
fun unexpectedValue(value: Any?): Nothing = throw IllegalStateException("Unexpected value: $value")

/** Throws an [IllegalStateException] with the passed message. */
fun illegal(errorMessage: String? = null): Nothing = throw IllegalStateException(errorMessage)

/** Throws an [IllegalArgumentException] with the passed message. */
fun illegalArg(errorMessage: String? = null): Nothing = throw IllegalArgumentException(errorMessage)

/** Throws an [IllegalArgumentException] with the passed [argument]. */
fun illegalArg(
    argument: Any?
): Nothing = throw IllegalArgumentException("Illegal argument: $argument")

/** Throws an [UnsupportedOperationException] with the passed message. */
fun unsupported(
    errorMessage: String? = null
): Nothing = throw UnsupportedOperationException(errorMessage)

/** Throws an [UnsupportedOperationException] with the unsupported action name in the message. */
fun unsupportedAction(intent: Intent): Nothing = unsupported("Unsupported action: ${intent.action}")
