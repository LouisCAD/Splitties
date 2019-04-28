/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties

import android.app.Application
import android.content.ContentProvider
import timber.log.Timber

/**
 * Initializes some app wide things (e.g. the logging library Timber).
 * This object needs to be invoked (`AppInit`) in a [ContentProvider] or an [Application].
 */
object AppInit {
    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
