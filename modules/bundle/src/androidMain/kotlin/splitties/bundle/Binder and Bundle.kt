/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.bundle

import android.os.Bundle
import android.os.IBinder
import kotlin.LazyThreadSafetyMode.PUBLICATION

/** @see [androidx.core.app.BundleCompat.putBinder] */
private val putIBinderMethod by lazy(PUBLICATION) {
    Bundle::class.java.getMethod("putIBinder", String::class.java, IBinder::class.java).also {
        it.isAccessible = true
    }
}

/** @see [androidx.core.app.BundleCompat.putBinder] */
internal fun Bundle.putBinderCompat(key: String, binder: IBinder) {
    putIBinderMethod.invoke(this, key, binder)
}
