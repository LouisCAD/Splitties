/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.yield
import splitties.activities.startActivity
import splitties.experimental.ExperimentalSplittiesApi
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi
import splitties.lifecycle.coroutines.awaitState

@ExperimentalSplittiesApi
suspend inline fun FragmentActivity.ensurePermission(
    permission: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
) {
    var askCount = 0
    if (Build.VERSION.SDK_INT < 23) return
    askLoop@ while (!hasPermission(permission)) {
        if (askCount > 0 ||
            showRationaleBeforeFirstAsk ||
            shouldShowRequestPermissionRationale(permission)
        ) {
            val quit = !showRationaleAndContinueOrReturn()
            if (quit) returnOrThrowBlock()
        }
        val result = requestPermission(permission); askCount++
        when (result) {
            PermissionRequestResult.Granted -> break@askLoop
            is PermissionRequestResult.Denied.MayAskAgain -> continue@askLoop
            is PermissionRequestResult.Denied.DoNotAskAgain -> {
                val goToSettings = askOpenSettingsOrReturn()
                if (goToSettings) {
                    openApplicationDetailsSettingsAndAwaitResumed(lifecycle)
                } else returnOrThrowBlock()
            }
        }
    }
}

@ExperimentalSplittiesApi
suspend inline fun Fragment.ensurePermission(
    permission: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
) {
    var askCount = 0
    if (Build.VERSION.SDK_INT < 23) return
    askLoop@ while (!hasPermission(permission)) {
        if (askCount > 0 ||
            showRationaleBeforeFirstAsk ||
            shouldShowRequestPermissionRationale(permission)
        ) {
            val quit = !showRationaleAndContinueOrReturn()
            if (quit) returnOrThrowBlock()
        }
        val result = requestPermission(permission); askCount++
        when (result) {
            PermissionRequestResult.Granted -> break@askLoop
            is PermissionRequestResult.Denied.MayAskAgain -> continue@askLoop
            is PermissionRequestResult.Denied.DoNotAskAgain -> {
                val goToSettings = askOpenSettingsOrReturn()
                if (goToSettings) {
                    val context = context
                        ?: throw CancellationException() //Not fatal in coroutines, no catch needed.
                    context.openApplicationDetailsSettingsAndAwaitResumed(lifecycle)
                } else returnOrThrowBlock()
            }
        }
    }
}

@PublishedApi
@UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
internal suspend fun Context.openApplicationDetailsSettingsAndAwaitResumed(lifecycle: Lifecycle) {
    startActivity(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) {
        data = "package:$packageName".toUri()
    }
    yield() // Allow the activity start to take effect and pause this activity
    lifecycle.awaitState(Lifecycle.State.RESUMED) // Await user coming back
}
