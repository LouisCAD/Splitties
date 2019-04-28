/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.yield
import splitties.activities.startActivity
import splitties.experimental.ExperimentalSplittiesApi
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi
import splitties.lifecycle.coroutines.awaitResumed

@ExperimentalSplittiesApi
suspend inline fun FragmentActivity.ensurePermission(
    permission: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    activity = this,
    fragmentManager = supportFragmentManager,
    lifecycle = lifecycle,
    permission = permission,
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

@ExperimentalSplittiesApi
suspend inline fun Fragment.ensurePermission(
    permission: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensurePermission(
    activity = requireActivity(),
    fragmentManager = requireFragmentManager(),
    lifecycle = lifecycle,
    permission = permission,
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

@ExperimentalSplittiesApi
suspend inline fun ensurePermission(
    activity: Activity,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
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
            activity.shouldShowRequestPermissionRationale(permission)
        ) {
            val quit = !showRationaleAndContinueOrReturn()
            if (quit) returnOrThrowBlock()
        }
        val result = requestPermission(fragmentManager, lifecycle, permission); askCount++
        when (result) {
            PermissionRequestResult.Granted -> break@askLoop
            is PermissionRequestResult.Denied.MayAskAgain -> continue@askLoop
            is PermissionRequestResult.Denied.DoNotAskAgain -> {
                val goToSettings = askOpenSettingsOrReturn()
                if (goToSettings) {
                    activity.openApplicationDetailsSettingsAndAwaitResumed(lifecycle)
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
    lifecycle.awaitResumed() // Await user coming back
}
