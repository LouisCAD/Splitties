package splitties.lifecycle.coroutines

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.asCoroutineDispatcher
import splitties.mainhandler.mainHandler

/**
 * Similar to [Dispatchers.Main] but without slow initialization caused by ServiceLoader doing I/O.
 *
 * TODO: Remove when [this issue](https://github.com/Kotlin/kotlinx.coroutines/issues/878) is resolved.
 *
 * Will be deprecated then removed when issue linked above is fixed.
 */
@Suppress("unused")
@MainDispatcherPerformanceIssueWorkaround
val Dispatchers.MainAndroid: CoroutineDispatcher
    get() = androidMainDispatcher
        ?: mainHandler.asCoroutineDispatcher("splitties-main-dispatcher").also {
            androidMainDispatcher = it
        }

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal var androidMainDispatcher: CoroutineDispatcher? = null
