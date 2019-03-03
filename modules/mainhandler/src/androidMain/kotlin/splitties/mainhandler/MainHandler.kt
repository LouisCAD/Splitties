/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.mainhandler

import android.os.Build.VERSION.SDK_INT
import android.os.Handler
import android.os.Looper
import splitties.mainthread.mainLooper

/**
 * Returns a [Handler] that is not subject to vsync delays.
 */
@JvmField
val mainHandler: Handler = if (SDK_INT >= 28) Handler.createAsync(mainLooper) else try {
    Handler::class.java.getDeclaredConstructor(
        Looper::class.java,
        Handler.Callback::class.java,
        Boolean::class.javaPrimitiveType // async
    ).newInstance(mainLooper, null, true)
} catch (ignored: NoSuchMethodException) {
    Handler(mainLooper) // Hidden constructor absent. Fall back to non-async constructor.
}
