/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

/**
 * Symbols having this annotation will be deprecated, then removed after AndroidX Lifecycle KTX
 * gets a similar API, which is under review as of January the 24th of 2019:
 * https://android-review.googlesource.com/c/platform/frameworks/support/+/869365/14
 */
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@Experimental(level = Experimental.Level.WARNING)
annotation class PotentialFutureAndroidXLifecycleKtxApi
