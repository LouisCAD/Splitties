/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:OptIn(InternalSplittiesApi::class)

package splitties.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.*
import splitties.bundle.withExtras
import splitties.experimental.ExperimentalSplittiesApi
import splitties.experimental.InternalSplittiesApi
import splitties.permissions.core.PermissionRequestFallbackActivity
import java.util.UUID
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds

/**
 * Returns immediately if the [permissions] are all already granted or if the device API level is
 * lower than 23 (runtime permissions were introduced in API level 23, and are granted at
 * app installation time on older Android API levels).
 *
 * In the [showRationaleAndContinueOrReturn] function, you should show the rationale for the
 * permission you are requesting, await for user acknowledgement, and return `true` if the user
 * agreed to continue. If you return `false` in that function, the [returnOrThrowBlock] function
 * will be executed and the permission won't be requested again.
 *
 * The [showRationaleBeforeFirstAsk] parameter is true by default. If the context in which the
 * permission is asked makes it absolutely obvious why it is required, then you should set it to
 * `false`. Such an example is asking for camera permission when the user pressed a button to start
 * recording a video.
 *
 * The [askOpenSettingsOrReturn] function is executed if the user ticked the "do not ask again"
 * checkbox and denied the permission. You can show a relevant message to the user if needed and
 * await for an interaction before returning a value. If you return `true`, the application details
 * will be opened in the device settings, and the user will be able to enable the permissions.
 * When the user comes back into your app, this function (`ensurePermission`) will return
 * immediately if the permission that was asked has been granted. Otherwise, the function will
 * show the rationale again (calling [showRationaleAndContinueOrReturn]), restarting the permission
 * request process. If you return `false`, the [returnOrThrowBlock] function will be executed and
 * the permission won't be requested again.
 *
 * The [returnOrThrowBlock] function is inline (like all the other functions you pass here), so if
 * you put a `return` expression in it, the function at call site will return. Note that the type
 * of a `return` expression is always [Nothing], regardless of the type of what is returned.
 * You can also throw if that better suits the way you handle the user cancelling the permission
 * grant.
 *
 * The passed [ComponentActivity] is used for 3 things:
 *
 * 1. To check whether we should show the rationale for the permission
 * request, and is also used in the case we need to open the application details settings to let the
 * user grant the permission in case the do not ask again checkbox was ticked.
 *
 * 2. To get a [Lifecycle] that will be used to await the resumed state before requesting the
 * permission, and will also be used to await user coming back from application details settings
 * if that path was required.
 *
 * 3. To get the [ActivityResultRegistry] that allows performing the permission request, and get
 * the result.
 */
@ExperimentalSplittiesApi
suspend inline fun ComponentActivity.ensureAllPermissions(
    vararg permissions: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
) = ensureAllPermissions(
    permissions = permissions,
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

/**
 * Returns immediately if the [permissions] are all already granted or if the device API level is
 * lower than 23 (runtime permissions were introduced in API level 23, and are granted at
 * app installation time on older Android API levels).
 *
 * In the [showRationaleAndContinueOrReturn] function, you should show the rationale for the
 * permission you are requesting, await for user acknowledgement, and return `true` if the user
 * agreed to continue. If you return `false` in that function, the [returnOrThrowBlock] function
 * will be executed and the permission won't be requested again.
 *
 * The [showRationaleBeforeFirstAsk] parameter is true by default. If the context in which the
 * permission is asked makes it absolutely obvious why it is required, then you should set it to
 * `false`. Such an example is asking for camera permission when the user pressed a button to start
 * recording a video.
 *
 * The [askOpenSettingsOrReturn] function is executed if the user ticked the "do not ask again"
 * checkbox and denied the permission. You can show a relevant message to the user if needed and
 * await for an interaction before returning a value. If you return `true`, the application details
 * will be opened in the device settings, and the user will be able to enable the permissions.
 * When the user comes back into your app, this function (`ensurePermission`) will return
 * immediately if the permission that was asked has been granted. Otherwise, the function will
 * show the rationale again (calling [showRationaleAndContinueOrReturn]), restarting the permission
 * request process. If you return `false`, the [returnOrThrowBlock] function will be executed and
 * the permission won't be requested again.
 *
 * The [returnOrThrowBlock] function is inline (like all the other functions you pass here), so if
 * you put a `return` expression in it, the function at call site will return. Note that the type
 * of a `return` expression is always [Nothing], regardless of the type of what is returned.
 * You can also throw if that better suits the way you handle the user cancelling the permission
 * grant.
 *
 * The passed [ComponentActivity] is used for 3 things:
 *
 * 1. To check whether we should show the rationale for the permission
 * request, and is also used in the case we need to open the application details settings to let the
 * user grant the permission in case the do not ask again checkbox was ticked.
 *
 * 2. To get a [Lifecycle] that will be used to await the resumed state before requesting the
 * permission, and will also be used to await user coming back from application details settings
 * if that path was required.
 *
 * 3. To get the [ActivityResultRegistry] that allows performing the permission request, and get
 * the result.
 */
@ExperimentalSplittiesApi
suspend inline fun ComponentActivity.ensureAllPermissions(
    permissions: List<String>,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
) = ensureAllPermissions(
    permissions = permissions.toTypedArray(),
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

@PublishedApi
@JvmName("ensurePermissions")
@ExperimentalSplittiesApi
internal suspend inline fun ComponentActivity.ensureAllPermissions(
    permissions: Array<out String>,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
) {
    var askCount = 0
    if (SDK_INT < 23) return
    askLoop@ while (permissions.any { hasPermission(it).not() }) {
        if (askCount > 0 ||
            showRationaleBeforeFirstAsk ||
            permissions.any { shouldShowRequestPermissionRationale(it) }
        ) {
            val quit = showRationaleAndContinueOrReturn().not()
            if (quit) returnOrThrowBlock()
        }
        askCount++
        @Suppress("unchecked_cast")
        val result = requestAllPermissions(
            activity = this,
            askCount = askCount,
            permissions = permissions as Array<String>
        )
        when (result) {
            PermissionRequestResult.Granted -> break@askLoop
            is PermissionRequestResult.Denied.MayAskAgain -> continue@askLoop
            is PermissionRequestResult.Denied.DoNotAskAgain -> {
                val goToSettings = askOpenSettingsOrReturn()
                if (goToSettings) {
                    @OptIn(InternalSplittiesApi::class)
                    openApplicationDetailsSettingsAndAwaitResumed(lifecycle)
                } else returnOrThrowBlock()
            }
        }
    }
}

/**
 * Returns immediately if the [permission] is already granted or if the device API level is
 * lower than 23 (runtime permissions were introduced in API level 23, and are granted at
 * app installation time on older Android API levels).
 *
 * In the [showRationaleAndContinueOrReturn] function, you should show the rationale for the
 * permission you are requesting, await for user acknowledgement, and return `true` if the user
 * agreed to continue. If you return `false` in that function, the [returnOrThrowBlock] function
 * will be executed and the permission won't be requested again.
 *
 * The [showRationaleBeforeFirstAsk] parameter is true by default. If the context in which the
 * permission is asked makes it absolutely obvious why it is required, then you should set it to
 * `false`. Such an example is asking for camera permission when the user pressed a button to start
 * recording a video.
 *
 * The [askOpenSettingsOrReturn] function is executed if the user ticked the "do not ask again"
 * checkbox and denied the permission. You can show a relevant message to the user if needed and
 * await for an interaction before returning a value. If you return `true`, the application details
 * will be opened in the device settings, and the user will be able to enable the permissions.
 * When the user comes back into your app, this function (`ensurePermission`) will return
 * immediately if the permission that was asked has been granted. Otherwise, the function will
 * show the rationale again (calling [showRationaleAndContinueOrReturn]), restarting the permission
 * request process. If you return `false`, the [returnOrThrowBlock] function will be executed and
 * the permission won't be requested again.
 *
 * The [returnOrThrowBlock] function is inline (like all the other functions you pass here), so if
 * you put a `return` expression in it, the function at call site will return. Note that the type
 * of a `return` expression is always [Nothing], regardless of the type of what is returned.
 * You can also throw if that better suits the way you handle the user cancelling the permission
 * grant.
 *
 * The passed [ComponentActivity] is used for 3 things:
 *
 * 1. To check whether we should show the rationale for the permission
 * request, and is also used in the case we need to open the application details settings to let the
 * user grant the permission in case the do not ask again checkbox was ticked.
 *
 * 2. To get a [Lifecycle] that will be used to await the resumed state before requesting the
 * permission, and will also be used to await user coming back from application details settings
 * if that path was required.
 *
 * 3. To get the [ActivityResultRegistry] that allows performing the permission request, and get
 * the result.
 */
@ExperimentalSplittiesApi
suspend inline fun ComponentActivity.ensurePermission(
    permission: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
) {
    var askCount = 0
    if (SDK_INT < 23) return
    askLoop@ while (hasPermission(permission).not()) {
        if (askCount > 0 ||
            showRationaleBeforeFirstAsk ||
            shouldShowRequestPermissionRationale(permission)
        ) {
            val quit = !showRationaleAndContinueOrReturn()
            if (quit) returnOrThrowBlock()
        }
        askCount++
        val result = requestPermission(
            activity = this,
            askCount = askCount,
            permission = permission
        )
        when (result) {
            PermissionRequestResult.Granted -> break@askLoop
            is PermissionRequestResult.Denied.MayAskAgain -> continue@askLoop
            is PermissionRequestResult.Denied.DoNotAskAgain -> {
                val goToSettings = askOpenSettingsOrReturn()
                if (goToSettings) {
                    @OptIn(InternalSplittiesApi::class)
                    openApplicationDetailsSettingsAndAwaitResumed(lifecycle)
                } else returnOrThrowBlock()
            }
        }
    }
}

/**
 * Requests the passed [permission] if needed, and returns the [PermissionRequestResult].
 */
@ExperimentalSplittiesApi
suspend fun requestPermission(
    activity: ComponentActivity,
    askCount: Int,
    permission: String
): PermissionRequestResult {
    if (SDK_INT < 23 ||
        activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    ) {
        return PermissionRequestResult.Granted
    }

    return activity.requestPermissionsNow(askCount, arrayOf(permission))
}

@PublishedApi
internal suspend fun requestAllPermissions(
    activity: ComponentActivity,
    askCount: Int,
    permissions: Array<String>
): PermissionRequestResult {
    if (
        SDK_INT < 23 || permissions.all { permission ->
            activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        return PermissionRequestResult.Granted
    }

    return activity.requestPermissionsNow(askCount, permissions)
}

/**
 * As of Kotlin 1.3, `while (true)` evaluates to [Unit] instead of [Nothing] in lambdas, and using
 * `coroutineContext.ensureActive()` would add another line of boilerplate, so this inline extension
 * function can be handy. The fact that is is inline allows you to do a non local return just like
 * you would from a while loop.
 */
private suspend inline fun repeatWhileActive(block: () -> Unit): Nothing {
    while (true) {
        coroutineContext.ensureActive()
        block()
    }
}

/**
 * Needed to avoid getting a pending result and only get the current request result.
 */
private fun generateVolatileKey(
    permissions: Array<String>,
    isFallback: Boolean = false
): String = buildString {
    permissions.forEach { append(it); append('-') }
    if (isFallback) append("fallback-")
    append(UUID.randomUUID().toString())
}

@RequiresApi(23)
private suspend fun ComponentActivity.requestPermissionsNow(
    askCount: Int,
    permissions: Array<String>,
): PermissionRequestResult = run {
    require(askCount >= 1)
    var requestsCount = 0
    repeatWhileActive {
        val requestTime = SystemClock.elapsedRealtimeNanos()
        val (recognizedPermissions, grantResults) = activityResultRegistry.awaitResult(
            key = generateVolatileKey(permissions),
            contract = ReliablePermissionRequestContract()
        ) {
            it.launch(permissions)
            requestsCount++
        }
        checkAllPermissionsWereRecognized(
            requestedPermissions = permissions,
            recognizedPermissions = recognizedPermissions
        )
        if (grantResults.isEmpty()) return@run null // Go to fallback.
        val elapsedNanos = SystemClock.elapsedRealtimeNanos() - requestTime
        val result = grantResultsToPermissionRequestResult(
            askCount = askCount,
            requestsCount = requestsCount,
            timeSinceRequest = elapsedNanos.nanoseconds,
            permissions = permissions,
            grantResults = grantResults
        )
        if (result != null) return@run result
    }
} ?: run {
    var requestsCount = 0
    repeatWhileActive {
        val intent = activityResultRegistry.awaitResult(
            key = generateVolatileKey(permissions, isFallback = true),
            contract = PermissionRequestFallbackActivity.Contract()
        ) {
            it.launch(permissions)
            requestsCount++
        }
        val grantResults: IntArray
        val timeToResultNanos: Long
        intent.withExtras(PermissionRequestFallbackActivity.ResultSpec) {
            grantResults = PermissionRequestFallbackActivity.ResultSpec.grantResults
            timeToResultNanos = PermissionRequestFallbackActivity.ResultSpec.timeToResultNanos
        }
        val result = grantResultsToPermissionRequestResult(
            askCount = askCount,
            requestsCount = requestsCount,
            timeSinceRequest = timeToResultNanos.nanoseconds,
            permissions = permissions,
            grantResults = grantResults
        )
        if (result != null) return@run result
    }
}

@OptIn(ExperimentalContracts::class)
private suspend inline fun <I, O> ActivityResultRegistry.awaitResult(
    key: String,
    contract: ActivityResultContract<I, O>,
    launch: (launcher: ActivityResultLauncher<I>) -> Unit
): O {
    contract { callsInPlace(launch, InvocationKind.EXACTLY_ONCE) }
    val deferredResult = CompletableDeferred<O>()
    val launcher: ActivityResultLauncher<I> = register(key, contract) {
        deferredResult.complete(it)
    }
    try {
        launch(launcher)
        return deferredResult.await()
    } finally {
        launcher.unregister()
    }
}

private class ReliablePermissionRequestContract :
    ActivityResultContract<Array<String>, Pair<Array<String>, IntArray>>() {

    override fun createIntent(context: Context, input: Array<String>): Intent {
        return RequestMultiplePermissions().createIntent(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Array<String>, IntArray> {
        val recognizedPermissions: Array<String>
        val grantResults: IntArray
        with(RequestMultiplePermissions) {
            // Values below always provided. See ComponentActivity.onRequestPermissionsResult(…)
            val extras = intent!!.extras!!
            recognizedPermissions = extras.getStringArray(EXTRA_PERMISSIONS)!!
            grantResults = extras.getIntArray(EXTRA_PERMISSION_GRANT_RESULTS)!!
        }
        return recognizedPermissions to grantResults
    }
}

@RequiresApi(23)
private fun Activity.grantResultsToPermissionRequestResult(
    askCount: Int,
    requestsCount: Int,
    timeSinceRequest: Duration,
    permissions: Array<String>,
    grantResults: IntArray
): PermissionRequestResult? {
    require(askCount >= 1)
    if (grantResults.isEmpty()) return null
    val indexOfFirstNotGranted = grantResults.indexOfFirst {
        it != PackageManager.PERMISSION_GRANTED
    }
    return if (indexOfFirstNotGranted == -1) {
        PermissionRequestResult.Granted
    } else permissions[indexOfFirstNotGranted].let { firstDeniedPermission ->
        val shouldShowRationale = shouldShowRequestPermissionRationale(firstDeniedPermission)
        // Uncomment the code below and add toast dependency for real-device testing.
//        val debugText = buildString {
//            if (shouldShowRationale) {
//                append("Should show rationale")
//                append(" | ")
//            }
//            append("Time since request: $timeSinceRequest")
//            append(" [$requestsCount]")
//        }
//        toast(debugText)
        if (shouldShowRationale) {
            //TODO: Store it somewhere, if we can find a good use for it.
            PermissionRequestResult.Denied.MayAskAgain(firstDeniedPermission)
        } else if (SDK_INT < Build.VERSION_CODES.R) {
            // Before Android 11, we can rely on the value of shouldShowRequestPermissionRationale.
            PermissionRequestResult.Denied.DoNotAskAgain(firstDeniedPermission)
        } else if (timeSinceRequest >= 200.milliseconds) {
            // We assume the popup was shown and the user took some time to react.
            // Too bad if it's in "never ask again" mode and is the device is that slow,
            // We might loop the user in such a case.
            PermissionRequestResult.Denied.MayAskAgain(firstDeniedPermission)
        } else if (timeSinceRequest >= 90.milliseconds) {
            // Might be the user quickly dismissing the popup,
            // but it's more likely to be that we're in the "Don't ask again" mode on a slow device.
            when {
                askCount <= 2 && requestsCount == 1 -> { // Don't loop more than twice.
                    // Tentatively, just in case we're on a slow device and it's first loop.
                    PermissionRequestResult.Denied.MayAskAgain(firstDeniedPermission)
                }
                else -> PermissionRequestResult.Denied.DoNotAskAgain(firstDeniedPermission)
            }
        } else {
            // Impossible to know if it's second "Don't allow" treated as Don't ask again,
            // or whether it's just slow dismiss by the user on first as,
            // so for those cases where the timeSinceRequest is not super short,
            // we might want to ask again, making sure we don't end up in an infinite loop.
            // For that (infinite loop avoidance), we can either check the timeSinceRequest,
            // which might be brittle on low end devices, or just keep a count of how many
            // requests we have been denied so far.
            when (requestsCount) {
                1 -> null // Retry to be sure it's in "Don't ask again" mode.
                else -> PermissionRequestResult.Denied.DoNotAskAgain(firstDeniedPermission)
            }
        }
    }
}

private fun checkAllPermissionsWereRecognized(
    requestedPermissions: Array<String>,
    recognizedPermissions: Array<String>
) {
    if (recognizedPermissions contentEquals requestedPermissions) return
    val hiddenPermissions = requestedPermissions.asList() - recognizedPermissions.toSet()
    val errorMessage: String = buildString {
        val unrecognized = hiddenPermissions.size
        val requested = requestedPermissions.size
        if (hiddenPermissions.size == 1) when (val hiddenPermission = hiddenPermissions.single()) {
            Manifest.permission.POST_NOTIFICATIONS -> {
                // This is the only actual special case we found so far, despite trying with other
                // permissions introduced in API 33. This behavior is (un)surprisingly undocumented.
                appendLine("The app's targetSdk must be 33 or greater to be able to request the POST_NOTIFICATIONS permission.")
            }
            else -> {
                // We have yet to find such a case beyond POST_NOTIFICATIONS, but just in case…
                append("The app's targetSdk is too low, the following permission cannot be granted: ")
                append(hiddenPermission)
                appendLine('.')
            }
        } else {
            appendLine("The targetSdk is too low, the following permissions cannot be granted:")
            hiddenPermissions.forEach { appendLine("- $it") }
        }
        if (unrecognized != requested) {
            appendLine("For reference, the following $requested permissions were requested:")
            requestedPermissions.forEach { appendLine("- $it") }
        }
    }
    throw IllegalArgumentException(errorMessage)
}
