/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.louiscad.splittiessample.preview.permissions

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
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
