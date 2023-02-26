/*
 * Copyright 2019-2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions.internal

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CompletableDeferred
import splitties.bundle.withExtras
import splitties.experimental.ExperimentalSplittiesApi
import splitties.experimental.InternalSplittiesApi
import splitties.intents.intent
import splitties.lifecycle.coroutines.createJob
import splitties.permissions.PermissionRequestResult
import splitties.permissions.core.PermissionRequestFallbackActivity

@RequiresApi(23)
@OptIn(InternalSplittiesApi::class)
internal class PermissionRequestDialogFragment : DialogFragment() {

    var permissionNames: Array<out String>? = null

    suspend fun awaitResult(): PermissionRequestResult = try {
        asyncGrant.await()
    } finally {
        runCatching { dismissAllowingStateLoss() } // Activity may be detached, so we catch.
    }

    @OptIn(ExperimentalSplittiesApi::class)
    private val asyncGrant = CompletableDeferred<PermissionRequestResult>(lifecycle.createJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionNames?.also {
            @Suppress("Deprecation") // We don't need the ActivityResultContract APIs.
            requestPermissions(it, 1)
        } ?: dismissAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        handleGrantResult(grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        checkNotNull(data) // data is always provided from PermissionRequestFallbackActivity result.
        handleGrantResult(
            grantResults = data.withExtras(PermissionRequestFallbackActivity.ResultSpec) {
                grantResults
            }
        )
    }

    private fun handleGrantResult(grantResults: IntArray) {
        val permissions = permissionNames ?: return
        if (grantResults.isEmpty()) {
            fallbackRequestFromTransparentActivity(permissions)
            return
        }
        try {
            val indexOfFirstNotGranted = grantResults.indexOfFirst {
                it != PackageManager.PERMISSION_GRANTED
            }
            val result = if (indexOfFirstNotGranted == -1) {
                PermissionRequestResult.Granted
            } else permissions[indexOfFirstNotGranted].let { firstDeniedPermission ->
                if (shouldShowRequestPermissionRationale(firstDeniedPermission)) {
                    PermissionRequestResult.Denied.MayAskAgain(firstDeniedPermission)
                } else {
                    PermissionRequestResult.Denied.DoNotAskAgain(firstDeniedPermission)
                }
            }
            asyncGrant.complete(result)
        } finally {
            dismissAllowingStateLoss()
        }
    }

    private fun fallbackRequestFromTransparentActivity(permissions: Array<out String>) {
        @Suppress("Deprecation") // We don't need the ActivityResultContract APIs.
        startActivityForResult(PermissionRequestFallbackActivity.intent { _, extrasSpec ->
            extrasSpec.permissionNames = permissions
        }, 1)
    }
}
