/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.recyclerview

import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "This API, when used wrongly can lead to coroutines that never resume. " +
        "To avoid this issue, for a same ListAdapter, always wait for the previous call to complete," +
        "or cancel it."
)
annotation class SubmitListAndAwaitWarning

@SubmitListAndAwaitWarning
suspend fun <T> ListAdapter<T, *>.submitListAndAwaitCommit(list: List<T>) {
    suspendCancellableCoroutine { c ->
        submitList(list) { c.resume(Unit) }
    }
}
