/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import splitties.permissions.internal.PermissionRequestDialogFragment
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.show
import splitties.init.appCtx
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi

/**
 * Returns true if the passed [permission] is granted, or if the device API level is lower than 23
 * (runtime permissions were introduced in API level 23, and are granted at app installation time on
 * older Android API levels).
 */
@ExperimentalSplittiesApi
fun hasPermission(permission: String): Boolean = SDK_INT < 23 ||
        appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

/**
 * Requests the passed [permission] if needed, and returns the [PermissionRequestResult].
 */
suspend inline fun FragmentActivity.requestPermission(
    permission: String
): PermissionRequestResult = requestPermission(supportFragmentManager, lifecycle, permission)

/**
 * Requests the passed [permission] if needed, and returns the [PermissionRequestResult].
 */
suspend inline fun Fragment.requestPermission(
    permission: String
): PermissionRequestResult = requestPermission(requireFragmentManager(), lifecycle, permission)

/**
 * Requests the passed [permission] if needed, and returns the [PermissionRequestResult].
 */
suspend fun requestPermission(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    permission: String
): PermissionRequestResult {
    if (SDK_INT < 23 ||
        appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    ) {
        return PermissionRequestResult.Granted
    }
    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    return fragmentManager.show(lifecycle, ::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitResult()
}
