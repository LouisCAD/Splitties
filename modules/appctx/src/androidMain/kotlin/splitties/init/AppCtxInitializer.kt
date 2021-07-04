/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.init

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer

/**
 * Initializes [appCtx] so it can be used where any [Context] can be used.
 *
 * If you use [appCtx] in another process than the default one. This will not be invoked.
 * The library will attempt to initialize [appCtx] using reflection instead. If you want to avoid
 * this, you can declare a subclass of this class in your manifest with the name of the process
 * properly specified to get it Automatically initialized. Alternatively, you can in your properly
 * declared custom [Application] call [injectAsAppCtx] passing `this` reference from the constructor
 * or the [Application.onCreate] method.
 */
@Keep // Needed because there's no default R8/proguard rules for App Startup Initializers.
@Suppress("unused") // False positive. Used in the manifest, but tooling analysis is incomplete.
internal class AppCtxInitializer : Initializer<AppCtxInitializer> {
    override fun create(context: Context): AppCtxInitializer {
        context.injectAsAppCtx()
        return this
    }

    override fun dependencies() = emptyList<Nothing>()
}
