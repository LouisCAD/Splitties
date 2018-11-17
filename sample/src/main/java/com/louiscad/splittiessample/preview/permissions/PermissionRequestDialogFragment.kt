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
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import kotlinx.coroutines.CompletableDeferred

@RequiresApi(23)
class PermissionRequestDialogFragment : DialogFragment() {

    var permissionName: String? = null
    suspend fun awaitGrant() = asyncGrant.await()

    private val asyncGrant = CompletableDeferred<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionName?.also {
            requestPermissions(arrayOf(it), 0)
        } ?: dismissAllowingStateLoss()
    }

    override fun onDestroy() {
        asyncGrant.cancel()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permission = permissionName ?: return
        val grantResult = grantResults[0]
        if (grantResult == PackageManager.PERMISSION_GRANTED) asyncGrant.complete(Unit)
        else asyncGrant.completeExceptionally(
            PermissionDeniedException(
                permissionName = permission,
                doNotAskAgain = !shouldShowRequestPermissionRationale(permission)
            )
        )
        dismissAllowingStateLoss()
    }
}
