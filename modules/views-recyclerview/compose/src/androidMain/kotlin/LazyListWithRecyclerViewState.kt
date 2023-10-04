/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.recyclerview.compose

import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import splitties.views.recyclerview.isVertical

@Composable
fun rememberLazyListWithRecyclerViewState(
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListWithRecyclerViewState {
    return rememberSaveable(saver = LazyListWithRecyclerViewState.Saver) {
        LazyListWithRecyclerViewState(
            firstVisibleItemIndex = initialFirstVisibleItemIndex,
            firstVisibleItemScrollOffset = initialFirstVisibleItemScrollOffset
        )
    }
}

class LazyListWithRecyclerViewState(
    firstVisibleItemIndex: Int = 0,
    firstVisibleItemScrollOffset: Int = 0
) {

    suspend fun bindToRecyclerViewUntilCancelled(
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager?
    ): Nothing {
        try {
            check(hostView == null)
            hostView = recyclerView
            this.layoutManager = layoutManager
            recyclerView.addOnScrollListener(listener)
            recyclerView.scrollToPosition(firstVisibleItemIndex)
            when {
                layoutManager?.isVertical != false -> {
                    recyclerView.scrollBy(0, firstVisibleItemScrollOffset)
                }
                else -> recyclerView.scrollBy(firstVisibleItemScrollOffset, 0)
            }
            awaitCancellation()
        } finally {
            recyclerView.removeOnScrollListener(listener)
            hostView = null
            this.layoutManager = null
        }
    }

    private var hostView: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null

    private val listener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) = Unit
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val lm = layoutManager ?: return
            val visiblePosition = lm.findFirstVisibleItemPosition()
            scrollPosition.index = visiblePosition
            scrollPosition.scrollOffset = lm.findViewByPosition(visiblePosition)?.let { visibleView ->
                visibleView.getLocalVisibleRect(firstVisibleItemRect)
                firstVisibleItemRect.top
            } ?: 0
        }
    }

    private val firstVisibleItemRect = Rect()

    private val scrollPosition = LazyListScrollPosition(
        initialIndex = firstVisibleItemIndex,
        initialScrollOffset = firstVisibleItemScrollOffset
    )

    val firstVisibleItemIndex: Int get() = scrollPosition.index

    val firstVisibleItemScrollOffset: Int get() = scrollPosition.scrollOffset

    companion object {
        val Saver: Saver<LazyListWithRecyclerViewState, *> = listSaver(
            save = { listOf(it.firstVisibleItemIndex, it.firstVisibleItemScrollOffset) },
            restore = {
                LazyListWithRecyclerViewState(
                    firstVisibleItemIndex = it[0],
                    firstVisibleItemScrollOffset = it[1]
                )
            }
        )
    }
}
