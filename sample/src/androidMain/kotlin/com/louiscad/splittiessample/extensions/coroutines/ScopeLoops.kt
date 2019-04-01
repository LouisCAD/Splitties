/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample.extensions.coroutines

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.ensureActive

suspend inline fun repeatWhileActive(block: () -> Unit): Nothing {
    while (true) {
        coroutineContext.ensureActive()
        block()
    }
}
