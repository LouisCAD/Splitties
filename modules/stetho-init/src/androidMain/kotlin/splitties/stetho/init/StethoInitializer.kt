/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.stetho.init

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.facebook.stetho.Stetho

@Keep // Needed because there's no default R8/proguard rules for App Startup Initializers.
@Suppress("unused") // False positive. Used in the manifest, but tooling analysis is incomplete.
internal class StethoInitializer : Initializer<StethoInitializer> {

    override fun create(context: Context): StethoInitializer {
        Stetho.initializeWithDefaults(context)
        return this
    }

    override fun dependencies() = emptyList<Nothing>()
}
