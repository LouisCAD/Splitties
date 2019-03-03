/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.louiscad.splittiessample.preview.permissions

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.louiscad.splittiessample.extensions.coroutines.show
import splitties.init.appCtx
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi

fun hasPermission(permission: String): Boolean = SDK_INT < 23 ||
        appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

@Throws(PermissionDeniedException::class)
suspend fun FragmentActivity.requestPermission(permission: String) {
    if (SDK_INT < 23) return
    if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) return
    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    show(::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitGrant()
}

@Throws(PermissionDeniedException::class)
suspend fun Fragment.requestPermission(permission: String) {
    if (SDK_INT < 23) return
    if (activity!!.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) return
    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    show(::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitGrant()
}
