package splitties.views.recyclerview.compose.genericmotionevent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun ProvideGenericMotionEventDispatcher(
    dispatcher: GenericMotionEventDispatcher,
    block: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalGenericMotionEventDispatcher provides dispatcher) {
        block()
    }
}

val LocalGenericMotionEventDispatcher = staticCompositionLocalOf<GenericMotionEventDispatcher?> {
    null
}
