/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.louiscad.splittiessample.preview.permissions

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.louiscad.splittiessample.extensions.coroutines.show
import splitties.init.appCtx

fun hasPermission(permission: String): Boolean = SDK_INT < 23 ||
        appCtx.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

@Throws(PermissionDeniedException::class)
suspend fun FragmentActivity.requestPermission(permission: String) {
    if (SDK_INT < 23) return
    if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) return
    show(::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitGrant()
}

@Throws(PermissionDeniedException::class)
suspend fun Fragment.requestPermission(permission: String) {
    if (SDK_INT < 23) return
    if (activity!!.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) return
    show(::PermissionRequestDialogFragment) {
        permissionName = permission
    }.awaitGrant()
}
