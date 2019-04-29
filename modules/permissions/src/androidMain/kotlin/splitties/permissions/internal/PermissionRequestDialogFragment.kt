/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.permissions.internal

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CompletableDeferred
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi
import splitties.lifecycle.coroutines.createJob
import splitties.permissions.PermissionRequestResult

@RequiresApi(23)
internal class PermissionRequestDialogFragment : DialogFragment() {

    var permissionName: String? = null
    suspend fun awaitResult(): PermissionRequestResult = asyncGrant.await()

    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    private val asyncGrant = CompletableDeferred<PermissionRequestResult>(lifecycle.createJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionName?.also {
            requestPermissions(arrayOf(it), 0)
        } ?: dismissAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permission = permissionName ?: return
        val grantResult = grantResults[0]
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            asyncGrant.complete(PermissionRequestResult.Granted)
        } else asyncGrant.complete(
            if (shouldShowRequestPermissionRationale(permission)) {
                PermissionRequestResult.Denied.MayAskAgain(permission)
            } else {
                PermissionRequestResult.Denied.DoNotAskAgain(permission)
            }
        )
        dismissAllowingStateLoss()
    }
}
