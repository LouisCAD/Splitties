package splitties.lifecycle.coroutines

import kotlinx.coroutines.Dispatchers

/**
 * Symbols with this annotation will eventually be deprecated, then removed when the performance
 * issue in kotlinx.coroutines is resolved.
 *
 * See [Dispatchers.MainAndroid] documentation for more info.
 */
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@Experimental(level = Experimental.Level.ERROR)
annotation class MainDispatcherPerformanceIssueWorkaround
