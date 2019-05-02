/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.permissions.internal

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CompletableDeferred
import splitties.intents.intent
import splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi
import splitties.lifecycle.coroutines.createJob
import splitties.permissions.PermissionRequestResult

@RequiresApi(23)
internal class PermissionRequestDialogFragment : DialogFragment() {

    var permissionName: String? = null
    suspend fun awaitResult(): PermissionRequestResult = try {
        asyncGrant.await()
    } finally {
        runCatching { dismissAllowingStateLoss() } // Activity may be detached, so we catch.
    }

    @UseExperimental(PotentialFutureAndroidXLifecycleKtxApi::class)
    private val asyncGrant = CompletableDeferred<PermissionRequestResult>(lifecycle.createJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionName?.also {
            requestPermissions(arrayOf(it), 1)
        } ?: dismissAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        handleGrantResult(grantResults.getOrNull(0))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        checkNotNull(data) // data is always provided from PermissionRequestFallbackActivity result.
        val extras = data.extras!! // and its result always contains the grantResult extra.
        handleGrantResult(extras.get(PermissionRequestFallbackActivity.GRANT_RESULT) as Int?)
    }

    private fun handleGrantResult(grantResult: Int?) {
        val permission = permissionName ?: return
        if (grantResult == null) {
            fallbackRequestFromTransparentActivity(permission)
            return
        }
        try {
            when (grantResult) {
                PackageManager.PERMISSION_GRANTED -> {
                    asyncGrant.complete(PermissionRequestResult.Granted)
                }
                else -> asyncGrant.complete(
                    value = if (shouldShowRequestPermissionRationale(permission)) {
                        PermissionRequestResult.Denied.MayAskAgain(permission)
                    } else {
                        PermissionRequestResult.Denied.DoNotAskAgain(permission)
                    }
                )
            }
        } finally {
            dismissAllowingStateLoss()
        }
    }

    private fun fallbackRequestFromTransparentActivity(permission: String) {
        startActivityForResult(PermissionRequestFallbackActivity.intent { _, extrasSpec ->
            extrasSpec.permissionName = permission
        }, 1)
    }
}
