/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
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
val Dispatchers.MainAndroid: MainCoroutineDispatcher
    get() = androidMainDispatcher ?: try {
        mainHandler.asCoroutineDispatcher("splitties-main-dispatcher")
    } catch (cantCheckMainThreadInTestsError: ExceptionInInitializerError) {
        Dispatchers.Main
    } catch (cantCheckMainThreadInTestsError: NoClassDefFoundError) {
        Dispatchers.Main
    }.also {
        androidMainDispatcher = it
    }

private var androidMainDispatcher: MainCoroutineDispatcher? = null
