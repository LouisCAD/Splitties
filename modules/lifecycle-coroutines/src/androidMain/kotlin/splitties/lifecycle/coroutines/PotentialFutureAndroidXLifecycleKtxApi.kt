/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

/**
 * Symbols that had this annotation have been deprecated for AndroidX Lifecycle runtime KTX.
 * They will be removed along with this annotation in Splitties 3.0.0.
 *
 * BTW, Google's AndroidX team released it as stable about a year after it was in review in AOSP...
 * Depending on AndroidX as a library author requires a lot of patience as of 2019/2020.
 * Let's hope it gets better in the future.
 *
 * Good that Splitties can bring advances earlier!
 */
@Deprecated("AndroidX Lifecycle runtime KTX has now been released.", level = DeprecationLevel.HIDDEN)
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
annotation class PotentialFutureAndroidXLifecycleKtxApi

