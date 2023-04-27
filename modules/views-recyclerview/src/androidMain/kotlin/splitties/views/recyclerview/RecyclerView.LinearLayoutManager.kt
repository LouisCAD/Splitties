package splitties.views.recyclerview

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun LinearLayoutManager.fullSideListLayoutParams(
    block: RecyclerView.LayoutParams.() -> Unit = {}
): RecyclerView.LayoutParams = generateDefaultLayoutParams().apply {
    if (isVertical) width = MATCH_PARENT
    else height = MATCH_PARENT
}.apply(block)

inline val LinearLayoutManager.isVertical get() = orientation == LinearLayoutManager.VERTICAL
