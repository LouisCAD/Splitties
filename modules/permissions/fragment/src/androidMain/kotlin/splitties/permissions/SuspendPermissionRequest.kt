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
import splitties.fragments.show
import splitties.init.appCtx
import splitties.permissions.internal.PermissionRequestDialogFragment

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
): PermissionRequestResult = requestPermission(parentFragmentManager, lifecycle, permission)

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
    return fragmentManager.show(lifecycle, ::PermissionRequestDialogFragment) {
        permissionNames = arrayOf(permission)
    }.awaitResult()
}

/**
 * Requests the passed [permissions] if needed, and returns the [PermissionRequestResult].
 */
@PublishedApi
@JvmName("requestPermissions")
internal suspend fun requestAllPermissions(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    permissions: Array<out String>
): PermissionRequestResult {
    if (SDK_INT < 23 ||
        permissions.all { permission ->
            appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        return PermissionRequestResult.Granted
    }
    return fragmentManager.show(lifecycle, ::PermissionRequestDialogFragment) {
        permissionNames = permissions
    }.awaitResult()
}
