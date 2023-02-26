/*
 * Copyright 2019-2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

/**
 * Represents the result of an Android runtime permission request.
 *
 * Returned by [requestPermission].
 */
sealed class PermissionRequestResult {
    object Granted : PermissionRequestResult()
    sealed class Denied(val deniedPermission: String) : PermissionRequestResult() {
        class MayAskAgain(deniedPermission: String) : Denied(deniedPermission)
        class DoNotAskAgain(deniedPermission: String) : Denied(deniedPermission)
    }
}
