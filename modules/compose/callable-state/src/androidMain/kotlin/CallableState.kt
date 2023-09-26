@file:Suppress("nothing_to_inline")

package splitties.androidx.compose

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*
import splitties.collections.forEachByIndex
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

@Composable
inline fun <T> rememberCallableState(): CallableState<T> {
    return remember { CallableState() }
}

@Stable //TODO: Report Compose compiler crash when this is made an inline value class.
class CallableState<T> private constructor(
    @PublishedApi
    internal val awaiters: SnapshotStateList<Continuation<T>>
) {

    constructor() : this(mutableStateListOf())

    suspend fun awaitOneCall(): T = suspendCancellableCoroutine { c ->
        awaiters.add(c)
        c.invokeOnCancellation { awaiters.remove(c) }
    }

    operator fun invoke(newValue: T): Boolean {
        val list = Snapshot.withMutableSnapshot {
            val result = awaiters.toList()
            awaiters.clear()
            result
        }
        list.forEachByIndex { it.resume(newValue) }
        return list.isNotEmpty()
    }

    inline val awaitersCount: Int get() = awaiters.size
    inline val awaitsCall: Boolean get() = awaiters.isNotEmpty()
}

@Suppress("nothing_to_inline")
inline operator fun CallableState<Unit>.invoke(): Boolean = invoke(Unit)

@Suppress("nothing_to_inline")
inline fun CallableState<Unit>.asCallable(): () -> Unit = { invoke() }
