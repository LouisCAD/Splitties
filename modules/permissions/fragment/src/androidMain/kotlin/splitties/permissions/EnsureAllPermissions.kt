/*
 * Copyright 2019-2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import splitties.experimental.ExperimentalSplittiesApi
import splitties.experimental.InternalSplittiesApi

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
 */
@ExperimentalSplittiesApi
suspend inline fun FragmentActivity.ensureAllPermissions(
    vararg permissions: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    activity = this,
    fragmentManager = supportFragmentManager,
    lifecycle = lifecycle,
    permissions = permissions,
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

/**
 * Returns immediately if the [permissionNames] are all already granted or if the device API level is
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
 */
@ExperimentalSplittiesApi
suspend inline fun FragmentActivity.ensureAllPermissions(
    permissionNames: List<String>,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    activity = this,
    fragmentManager = supportFragmentManager,
    lifecycle = lifecycle,
    permissionNames = permissionNames,
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
 */
@ExperimentalSplittiesApi
suspend inline fun Fragment.ensureAllPermissions(
    vararg permissions: String,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    activity = requireActivity(),
    fragmentManager = parentFragmentManager,
    lifecycle = lifecycle,
    permissions = permissions,
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

/**
 * Returns immediately if the [permissionNames] are all already granted or if the device API level is
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
 */
@ExperimentalSplittiesApi
suspend inline fun Fragment.ensureAllPermissions(
    permissionNames: List<String>,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    activity = requireActivity(),
    fragmentManager = parentFragmentManager,
    lifecycle = lifecycle,
    permissionNames = permissionNames,
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
 * The [activity] parameter is used to check whether we should show the rationale for the permission
 * request, and is also used in the case we need to open the application details settings to let the
 * user grant the permission in case the do not ask again checkbox was ticked.
 *
 * The [fragmentManager] is needed to use a Fragment under the hood that will allow us to receive
 * the result of the permission request.
 *
 * The [lifecycle] will be used to await the resumed state before requesting the permission, and
 * will also be used to await user coming back from application details settings if that path was
 * required.
 */
@ExperimentalSplittiesApi
suspend inline fun ensureAllPermissions(
    vararg permissions: String,
    activity: Activity,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    activity = activity,
    fragmentManager = fragmentManager,
    lifecycle = lifecycle,
    permissions = permissions,
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

/**
 * Returns immediately if the [permissionNames] are all already granted or if the device API level is
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
 * The [activity] parameter is used to check whether we should show the rationale for the permission
 * request, and is also used in the case we need to open the application details settings to let the
 * user grant the permission in case the do not ask again checkbox was ticked.
 *
 * The [fragmentManager] is needed to use a Fragment under the hood that will allow us to receive
 * the result of the permission request.
 *
 * The [lifecycle] will be used to await the resumed state before requesting the permission, and
 * will also be used to await user coming back from application details settings if that path was
 * required.
 */
@ExperimentalSplittiesApi
suspend inline fun ensureAllPermissions(
    permissionNames: List<String>,
    activity: Activity,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    showRationaleAndContinueOrReturn: () -> Boolean,
    showRationaleBeforeFirstAsk: Boolean = true,
    askOpenSettingsOrReturn: () -> Boolean,
    returnOrThrowBlock: () -> Nothing
): Unit = ensureAllPermissions(
    activity = activity,
    fragmentManager = fragmentManager,
    lifecycle = lifecycle,
    permissions = permissionNames.toTypedArray(),
    showRationaleAndContinueOrReturn = showRationaleAndContinueOrReturn,
    showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
    askOpenSettingsOrReturn = askOpenSettingsOrReturn,
    returnOrThrowBlock = returnOrThrowBlock
)

@ExperimentalSplittiesApi
@JvmName("ensurePermissions")
@PublishedApi
internal suspend inline fun ensureAllPermissions(
    activity: Activity,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
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
            permissions.any { activity.shouldShowRequestPermissionRationale(it) }
        ) {
            val quit = showRationaleAndContinueOrReturn().not()
            if (quit) returnOrThrowBlock()
        }
        val result = requestAllPermissions(fragmentManager, lifecycle, permissions); askCount++
        when (result) {
            PermissionRequestResult.Granted -> break@askLoop
            is PermissionRequestResult.Denied.MayAskAgain -> continue@askLoop
            is PermissionRequestResult.Denied.DoNotAskAgain -> {
                val goToSettings = askOpenSettingsOrReturn()
                if (goToSettings) {
                    @OptIn(InternalSplittiesApi::class)
                    activity.openApplicationDetailsSettingsAndAwaitResumed(lifecycle)
                } else returnOrThrowBlock()
            }
        }
    }
}
