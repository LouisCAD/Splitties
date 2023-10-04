package splitties.views.recyclerview.compose.hack

import android.content.Context
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*

@Composable
fun <V : View> rememberViewFactory(factory: (Context) -> V): AndroidViewFactory<V> {
    val result = remember(factory) {
        AndroidViewFactory(
            factory = factory
        )
    }
    DisposableEffect(result) { onDispose { result.clearView() } }
    return result
}

@Composable
fun <V : View> AndroidViewFactory<V>.RunWithViewUntilCancelled(
    block: suspend CoroutineScope.(V) -> Unit
) {
    val currentView = currentView
    LaunchedEffect(currentView, block) {
        if (currentView != null) block(currentView)
    }
}

@Stable
class AndroidViewFactory<V : View> internal constructor(
    private val factory: (Context) -> V
) : (Context) -> V {

    var currentView: V? by mutableStateOf(null)
        private set

    internal fun clearView() {
        currentView = null
    }

    override fun invoke(context: Context): V = factory(context).also { currentView = it }
}
