/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import splitties.permissions.internal.PermissionRequestDialogFragment
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.show
import splitties.init.appCtx
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi

@ExperimentalSplittiesApi
fun hasPermission(permission: String): Boolean = SDK_INT < 23 ||
        appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

suspend fun FragmentActivity.requestPermission(permission: String): PermissionRequestResult {
    if (SDK_INT < 23 ||
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    ) {
        return PermissionRequestResult.Granted
    }
    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    return show(::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitResult()
}

suspend fun Fragment.requestPermission(permission: String): PermissionRequestResult {
    if (SDK_INT < 23 ||
        activity!!.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    ) {
        return PermissionRequestResult.Granted
    }
    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    return show(::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitResult()
}
