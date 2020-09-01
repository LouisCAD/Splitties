/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import kotlinx.coroutines.Dispatchers

/**
 * Marked symbols that were working around a past performance issue in kotlinx.coroutines with
 * `Dispatchers.Main` that has been fixed in kotlinx.coroutines 1.3.3.
 *
 * See [Dispatchers.MainAndroid] documentation for more info.
 */
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
@Deprecated("No longer needed. Dispatchers.Main performance issue has been fixed since kotlinx.coroutines 1.3.3")
annotation class MainDispatcherPerformanceIssueWorkaround
