/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

@Suppress("unused") // Used in AndroidManifest.xml, bug in Android Studio.
class DemoApp : Application() {

    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        init {
            if (SDK_INT < 29) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }
    }
}
