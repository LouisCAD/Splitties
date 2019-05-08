/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainhandler

import android.os.Handler
import splitties.experimental.ExperimentalSplittiesApi
import splitties.mainthread.mainLooper

/**
 * Returns a [Handler] that is tied to vsync. You should use [mainHandler] instead,
 * unless you know you need vsync.
 */
@JvmField
@ExperimentalSplittiesApi
val mainHandlerSync = Handler(mainLooper)
