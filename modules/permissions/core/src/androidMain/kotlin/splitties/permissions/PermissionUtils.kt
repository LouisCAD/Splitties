/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.provider.Settings
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.*
import splitties.activities.startActivity
import splitties.experimental.ExperimentalSplittiesApi
import splitties.experimental.InternalSplittiesApi
import splitties.init.appCtx
import splitties.lifecycle.coroutines.awaitResumed

/**
 * Returns true if the passed [permission] is granted, or if the device API level is lower than 23
 * (runtime permissions were introduced in API level 23, and are granted at app installation time on
 * older Android API levels).
 */
@ExperimentalSplittiesApi
fun hasPermission(permission: String): Boolean = SDK_INT < 23 ||
    appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

@InternalSplittiesApi
@OptIn(ExperimentalSplittiesApi::class)
suspend fun Context.openApplicationDetailsSettingsAndAwaitResumed(lifecycle: Lifecycle) {
    startActivity(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) {
        data = "package:$packageName".toUri()
    }
    yield() // Allow the activity start to take effect and pause this activity
    lifecycle.awaitResumed() // Await user coming back
}
