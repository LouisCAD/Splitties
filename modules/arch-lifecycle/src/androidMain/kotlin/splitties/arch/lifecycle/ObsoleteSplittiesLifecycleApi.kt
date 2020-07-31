/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.lifecycle

/**
 * A similar API is being added in AndroidX KTX artifacts (in alpha as of 2018-12-17).
 * Symbols that have this annotation will eventually be deprecated, then removed, unless
 * they satisfy a use case worth keeping, that AndroidX doesn't.
 */
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
annotation class ObsoleteSplittiesLifecycleApi
