package splitties.views.recyclerview.compose

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import splitties.experimental.InternalSplittiesApi
import splitties.views.dsl.recyclerview.SingleViewAdapter
import splitties.views.dsl.recyclerview.recyclerView
import splitties.views.recyclerview.compose.genericmotionevent.LocalGenericMotionEventDispatcher
import splitties.views.recyclerview.compose.hack.RunWithViewUntilCancelled
import splitties.views.recyclerview.compose.hack.rememberViewFactory

@Composable
fun ColumnWithRecyclerViewScroll(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    focusRequester: FocusRequester = remember { FocusRequester() },
    requestFocus: Boolean = true,
    columnContentPadding: PaddingValues = PaddingValues(0.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable() (ColumnScope.() -> Unit)
) {
    val context = LocalContext.current
    val composeView = remember { ComposeView(context) }
    @OptIn(InternalSplittiesApi::class)
    val adapter = remember {
        SingleViewAdapter(composeView, vertical = true)
    }
    DisposableEffect(content as Any) {
        composeView.setContent {
            Column(
                modifier = Modifier.padding(columnContentPadding),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                content = content
            )
        }
        onDispose {}
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
        genericMotionEventDispatcher?.putViewUntilCancelled(view, isFocusedState)
    }
    @OptIn(InternalSplittiesApi::class)
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
            if (it.layoutManager != adapter.layoutManager) it.layoutManager = adapter.layoutManager
            if (it.adapter != adapter) it.adapter = adapter
        }
    )
    if (requestFocus) LaunchedEffect(Unit) { focusRequester.requestFocus() }
}
