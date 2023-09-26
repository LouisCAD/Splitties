package splitties.permissions.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.sync.*
import splitties.androidx.compose.CallableState
import splitties.permissions.ensureAllPermissions

@Composable
fun rememberPermissionRequestState(
    permission: String?,
    showRationaleBeforeFirstAsk: Boolean
): PermissionRequestState {
    val activity = LocalActivityResultRegistryOwner.current as ComponentActivity
    return remember(permission, activity) {
        PermissionRequestStateImpl(
            activity = activity,
            permissions = when (permission) {
                null -> emptyList()
                else -> listOf(permission)
            },
            showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk
        )
    }
}

@Composable
fun rememberPermissionsRequestState(
    permissions: List<String>,
    showRationaleBeforeFirstAsk: Boolean
): PermissionRequestState {
    val activity = LocalActivityResultRegistryOwner.current as ComponentActivity
    return remember(permissions, activity) {
        PermissionRequestStateImpl(
            activity = activity,
            permissions = permissions,
            showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk
        )
    }
}

@Stable
sealed interface PermissionRequestState {
    suspend fun attemptGetting(): Boolean
    val showRationaleAndContinueOrReturn: CallableState<Boolean>
    val askOpenSettingsOrReturn: CallableState<Boolean>
}

@Stable
private class PermissionRequestStateImpl(
    private val activity: ComponentActivity,
    private val permissions: List<String>,
    private val showRationaleBeforeFirstAsk: Boolean,
) : PermissionRequestState {

    override val showRationaleAndContinueOrReturn = CallableState<Boolean>()
    override val askOpenSettingsOrReturn = CallableState<Boolean>()
    private val mutex = Mutex()

    override suspend fun attemptGetting(): Boolean = mutex.withLock {
        if (permissions.isEmpty()) return true
        activity.ensureAllPermissions(
            permissions = permissions,
            showRationaleBeforeFirstAsk = showRationaleBeforeFirstAsk,
            showRationaleAndContinueOrReturn = {
                showRationaleAndContinueOrReturn.awaitOneCall()
            },
            askOpenSettingsOrReturn = {
                askOpenSettingsOrReturn.awaitOneCall()
            }
        ) {
            return false
        }
        return true
    }
}
