package splitties.views.recyclerview.compose

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.ConcatAdapter
import kotlinx.coroutines.*
import splitties.experimental.InternalSplittiesApi
import splitties.views.dsl.recyclerview.*
import splitties.views.recyclerview.ItemDiff
import splitties.views.recyclerview.SubmitListAndAwaitWarning
import splitties.views.recyclerview.adapters.LinearListAdapter
import splitties.views.recyclerview.compose.genericmotionevent.LocalGenericMotionEventDispatcher
import splitties.views.recyclerview.compose.hack.RunWithViewUntilCancelled
import splitties.views.recyclerview.compose.hack.rememberViewFactory
import splitties.views.recyclerview.submitListAndAwaitCommit
import splitties.views.recyclerview.verticalListAdapter

@Composable
fun <T : Any> LazyColumnWithRecyclerView(
    modifier: Modifier = Modifier,
    items: List<T>,
    getKey: (T) -> Any?,
    itemContent: @Composable (data: T) -> Unit
) {
    val adapter = remember(getKey) {
        verticalListAdapter(
            itemDiffCallback = ItemDiff.dataClassWithKey(getKey).toItemCallback(),
            createView = { ComposeView(it) },
            bindViewHolder = { vh, v, t ->
                v.setContent { itemContent(t) }
            }
        )
    }
    AndroidView(
        factory = { it.recyclerView() },
        modifier = modifier,
        update = {
            it.adapter = adapter
            it.layoutManager = adapter.layoutManager
        }
    )
    LaunchedEffect(items) {
        @OptIn(SubmitListAndAwaitWarning::class)
        adapter.submitListAndAwaitCommit(items)
    }
}

@OptIn(InternalSplittiesApi::class)
@Composable
fun LazyColumnWithRecyclerView(
    modifier: Modifier = Modifier,
    state: LazyListWithRecyclerViewState = rememberLazyListWithRecyclerViewState(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    requestFocus: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: RecyclerViewBackedLazyColumnScope.() -> Unit
) {
    val scope = remember {
        RecyclerViewBackedLazyColumnScope()
    }

    scope.update(content) // Necessary because content is not a composable function,
    // and the state it reads would not be detected by Compose unless it's run as
    // part of a Composable function, hence we doing it here.
    // Underlying optimizations of the differ that skips when it's the same list instance
    // avoid this incurring high performance penalties.

    val layoutManager = remember {
        scope.adapters.lastOrNull()?.layoutManager
    }
    val adapter = remember {
        scope.adapters.singleOrNull() ?: ConcatAdapter(scope.adapters)
    }
    val paddingLeft: Int
    val paddingTop: Int
    val paddingRight: Int
    val paddingBottom: Int
    with(LocalDensity.current) {
        val ld = LocalLayoutDirection.current
        paddingLeft = contentPadding.calculateLeftPadding(ld).roundToPx()
        paddingTop = contentPadding.calculateTopPadding().roundToPx()
        paddingRight = contentPadding.calculateRightPadding(ld).roundToPx()
        paddingBottom = contentPadding.calculateBottomPadding().roundToPx()
    }
    val genericMotionEventDispatcher = LocalGenericMotionEventDispatcher.current
    val recyclerViewFactory = rememberViewFactory {
        it.recyclerView { clipToPadding = false }
    }
    val isFocusedState = remember { mutableStateOf(false) }
    recyclerViewFactory.RunWithViewUntilCancelled { view ->
        view.adapter = adapter
        launch { state.bindToRecyclerViewUntilCancelled(view, layoutManager) }
        genericMotionEventDispatcher?.putViewUntilCancelled(view, isFocusedState)
    }
    AndroidView(
        factory = recyclerViewFactory,
        modifier = modifier.onFocusChanged {
            isFocusedState.value = it.hasFocus
        }.focusRequester(focusRequester).focusable(),
        update = {
            it.setPadding(
                paddingLeft,
                paddingTop,
                paddingRight,
                paddingBottom
            )
            if (it.layoutManager != layoutManager) it.layoutManager = layoutManager
        }
    )
    if (requestFocus) LaunchedEffect(Unit) { focusRequester.requestFocus() }
}

interface AdapterFactory {
    fun <T : Any> createAdapter(
        key: ((item: T) -> Any)? = null,
        itemContent: @Composable (index: Int, item: T) -> Unit
    ): LinearListAdapter<T, *>
}

private object DefaultAdapterFactory : AdapterFactory {
    override fun <T : Any> createAdapter(
        key: ((item: T) -> Any)?,
        itemContent: @Composable (index: Int, item: T) -> Unit
    ): LinearListAdapter<T, *> = verticalListAdapter(
        itemDiffCallback = ItemDiff.dataClassWithKey<T> {
            key?.invoke(this) ?: this
        }.toItemCallback(),
        createView = { ComposeView(it) },
        bindViewHolder = { vh, v, t: T ->
            v.setContent { itemContent(vh.bindingAdapterPosition, t) }
        }
    )
}

@OptIn(InternalSplittiesApi::class)
class RecyclerViewBackedLazyColumnScope(
    private val adapterFactory: AdapterFactory = DefaultAdapterFactory
) {
    @InternalSplittiesApi
    val adapters = mutableListOf<LinearListAdapter<*, *>>()

    @InternalSplittiesApi
    @PublishedApi
    internal val listsToSubmit = mutableListOf<List<*>>()

    @InternalSplittiesApi
    @PublishedApi
    internal var sealed = false

    @InternalSplittiesApi
    inline fun update(block: RecyclerViewBackedLazyColumnScope.() -> Unit) {
        try {
            block()
            if (sealed.not()) return
            check(adapters.size == listsToSubmit.size) {
                "Changing the number of calls to the items and item functions is not supported. " +
                        "Change the size of the lists passed to the items function, " +
                        "or write the condition within the block passed to the item function " +
                        "to ensure reliable results."
            }
            adapters.forEachIndexed { index, adapter ->
                @Suppress("UNCHECKED_CAST")
                val newList = listsToSubmit[index] as List<Nothing>
                adapter.submitList(newList)
            }
        } finally {
            listsToSubmit.clear()
            sealed = true
        }
    }

    inline fun <T : Any> items(
        items: List<T>,
        noinline key: ((item: T) -> Any)? = null,
        crossinline itemContent: @Composable (item: T) -> Unit
    ) = itemsIndexed(items, key) { _, item: T ->
        itemContent(item)
    }

    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        itemContent: @Composable (index: Int) -> Unit
    ) = itemsIndexed(List(count) { it }, key) { _, item: Int ->
        itemContent(item)
    }

    inline fun item(
        crossinline itemContent: @Composable () -> Unit
    ) = itemsIndexed(listOf(Unit)) { _, _ ->
        itemContent()
    }

    fun <T : Any> itemsIndexed(
        items: List<T>,
        key: ((item: T) -> Any)? = null,
        itemContent: @Composable (index: Int, item: T) -> Unit
    ) {
        if (sealed) listsToSubmit += items
        else adapters += adapterFactory.createAdapter(
            key = key,
            itemContent = itemContent
        ).also { it.submitList(items) }
    }
}
