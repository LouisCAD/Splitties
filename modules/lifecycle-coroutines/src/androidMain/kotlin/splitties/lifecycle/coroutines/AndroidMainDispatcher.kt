/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.lifecycle.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.android.*
import splitties.mainhandler.mainHandler

/**
 * This was a workaround for [an issue](https://github.com/Kotlin/kotlinx.coroutines/issues/878) in versions of
 * kotlinx.coroutines preceding the version 1.3.3, where [Dispatchers.Main] had a slow initialization caused by
 * ServiceLoader doing I/O.
 */
@Suppress("unused")
@Deprecated(
    "Since kotlinx.coroutines 1.3.3, Dispatchers.Main performance issue is fixed, so this is no longer needed.",
    ReplaceWith("Dispatchers.Main"),
    DeprecationLevel.HIDDEN
)
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
