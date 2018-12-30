package splitties.lifecycle.coroutines

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
    get() = androidMainDispatcher ?: try {
        mainHandler.asCoroutineDispatcher("splitties-main-dispatcher")
    } catch (cantCheckMainThreadInTestsError: ExceptionInInitializerError) {
        Dispatchers.Main
    } catch (cantCheckMainThreadInTestsError: NoClassDefFoundError) {
        Dispatchers.Main
    }.also {
        androidMainDispatcher = it
    }

private var androidMainDispatcher: CoroutineDispatcher? = null
