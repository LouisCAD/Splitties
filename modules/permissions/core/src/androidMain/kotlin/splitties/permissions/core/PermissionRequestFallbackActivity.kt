/*
 * Copyright 2019-2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import splitties.bundle.BundleSpec
import splitties.bundle.bundle
import splitties.bundle.bundleOrNull
import splitties.bundle.putExtras
import splitties.bundle.withExtras
import splitties.experimental.InternalSplittiesApi
import splitties.intents.ActivityIntentSpec
import splitties.intents.activitySpec
import splitties.intents.intent

/**
 * This Activity is internal to the Splitties permissions library.
 *
 * It is used as a fallback when the `grantResults` array received from [onRequestPermissionsResult]
 * is empty.
 *
 * Keep reading if you want to know more about why this fallback is necessary.
 *
 * A bug in Android can lead to inability to request a permission again from an `Activity` that has
 * received an empty `grantResults` once, as all subsequent attempts to call `requestPermissions`
 * lead to the same behavior, until a [StackOverflowError] strikes as its done inside the code of
 * the platform [Activity] class.
 *
 * This dead-end state is saved into instance state, so it persists across configuration changes or
 * attempts to call [recreate]. That's why the fallback in Splitties permissions uses a new,
 * transparent `Activity`, so it is clean from the dead-end state, and can be finished and restarted
 * if needed.
 *
 * The empty `grantResults` case can be reproduced with the "Don't keep activities" developer option
 * enabled by opening overview (recent apps system UI) while system permission prompt is shown, and
 * going back into the app under test. Press on deny is ignored, and subsequent permission request
 * from the same Activity or one of its child fragments triggers the bug where `grantResults` is
 * empty for the rest of the life of that `Activity`, with no prompt shown to the user. The system
 * thinks there's still an ongoing permission request for that `Activity` and rejects new requests
 * that way, while no prompt is on the screen anymore.
 */
@RequiresApi(23)
@InternalSplittiesApi
@RestrictTo(RestrictTo.Scope.LIBRARY) // Discourage usage from Java (should trigger lint)
class PermissionRequestFallbackActivity : Activity() {

    companion object : ActivityIntentSpec<PermissionRequestFallbackActivity, ExtrasSpec> by activitySpec(ExtrasSpec)

    object ExtrasSpec : BundleSpec() {
        var permissionNames: Array<out String>? by bundleOrNull()
    }

    class Contract : ActivityResultContract<Array<String>, Intent>() {
        override fun createIntent(context: Context, input: Array<String>): Intent {
            return PermissionRequestFallbackActivity.intent { _, extrasSpec ->
                extrasSpec.permissionNames = input
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Intent {
            return checkNotNull(intent) // always provided from onRequestPermissionsResult.
        }
    }

    object ResultSpec : BundleSpec() {
        var grantResults: IntArray by bundle()
        var timeToResultNanos: Long by bundle()
    }

    private var requestElapsedRealtimeNanos: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissions: Array<out String> = withExtras(ExtrasSpec) {
            permissionNames
        } ?: return finish() // Finish early if started without a permission to request.
        requestElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val timeToResultNanos = SystemClock.elapsedRealtimeNanos() - requestElapsedRealtimeNanos
        val result = Intent()
        result.putExtras(ResultSpec) {
            ResultSpec.grantResults = grantResults
            ResultSpec.timeToResultNanos = timeToResultNanos
        }
        setResult(RESULT_OK, result)
        finish()
    }
}
