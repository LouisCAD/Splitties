package splitties.views.recyclerview.compose.genericmotionevent

import android.view.MotionEvent
import android.view.View
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.*
import splitties.mainthread.checkMainThread

class GenericMotionEventDispatcher {

    fun dispatchGenericMotionEvent(ev: MotionEvent): Boolean {
        checkMainThread()
        val (targetView, _) = views.firstOrNull { (_, focusedState) ->
            focusedState.value
        } ?: return false
        return targetView.dispatchGenericMotionEvent(ev)
    }

    private val views = mutableListOf<Pair<View, MutableState<Boolean>>>()

    suspend fun putViewUntilCancelled(
        view: View,
        focusedState: MutableState<Boolean>
    ): Nothing = Dispatchers.Main.immediate {
        val pair = view to focusedState
        try {
            views.add(pair)
            awaitCancellation()
        } finally {
            views.remove(pair)
        }
    }
}
