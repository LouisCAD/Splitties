/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.permissions

sealed class PermissionRequestResult {
    object Granted : PermissionRequestResult()
    sealed class Denied(val deniedPermission: String) : PermissionRequestResult() {
        class MayAskAgain(deniedPermission: String) : Denied(deniedPermission)
        class DoNotAskAgain(deniedPermission: String) : Denied(deniedPermission)
    }
}
