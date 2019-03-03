/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.exceptions

import android.content.Intent

/** Throws an [IllegalStateException] with a message that includes [value]. */
fun unexpectedValue(value: Any?): Nothing = throw IllegalStateException("Unexpected value: $value")

@Deprecated("Use error from stdlib instead", ReplaceWith("error(errorMessage)"))
/** Throws an [IllegalStateException] with the passed message. */
fun illegal(errorMessage: String = ""): Nothing = error(errorMessage)

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
