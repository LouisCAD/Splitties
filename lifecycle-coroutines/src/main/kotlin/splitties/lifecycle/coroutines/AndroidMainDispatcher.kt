package splitties.lifecycle.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.asCoroutineDispatcher
import splitties.mainhandler.mainHandler

/**
 * Similar to [Dispatchers.Main] but without slow initialization caused by ServiceLoader doing I/O.
 * TODO: Remove when [this issue](https://github.com/Kotlin/kotlinx.coroutines/issues/878) is resolved.
 */
@Suppress("unused")
internal val Dispatchers.MainAndroid get() = androidMainDispatcher

private val androidMainDispatcher = mainHandler.asCoroutineDispatcher("splitties-main-dispatcher")
