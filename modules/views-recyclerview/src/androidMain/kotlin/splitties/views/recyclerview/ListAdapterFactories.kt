package splitties.views.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import splitties.typesaferecyclerview.ViewHolder
import splitties.views.horizontalMargin
import splitties.views.recyclerview.*
import splitties.views.recyclerview.adapters.LinearListAdapter
import splitties.views.verticalMargin

internal typealias BindViewHolder<V, T> = (
    viewHolder: RecyclerView.ViewHolder,
    view: V,
    item: T
) -> Unit

inline fun <T, VH : RecyclerView.ViewHolder> verticalListAdapter(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    horizontalMargin: Int = 0,
    verticalMargin: Int = 0,
    crossinline createViewHolder: (Context) -> VH,
    reverseLayout: Boolean = false,
    crossinline bindViewHolder: (viewHolder: VH, item: T) -> Unit
): LinearListAdapter<T, VH> = linearListAdapter(
    itemDiffCallback = itemDiffCallback,
    layoutManager = verticalLayoutManager(reverseLayout),
    horizontalMargin = horizontalMargin,
    verticalMargin = verticalMargin,
    createViewHolder = createViewHolder,
    bindViewHolder = bindViewHolder
)

inline fun <T, VH : RecyclerView.ViewHolder> horizontalListAdapter(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    horizontalMargin: Int = 0,
    verticalMargin: Int = 0,
    crossinline createViewHolder: (Context) -> VH,
    reverseLayout: Boolean = false,
    crossinline bindViewHolder: (viewHolder: VH, item: T) -> Unit
): LinearListAdapter<T, VH> = linearListAdapter(
    itemDiffCallback = itemDiffCallback,
    layoutManager = horizontalLayoutManager(reverseLayout),
    horizontalMargin = horizontalMargin,
    verticalMargin = verticalMargin,
    createViewHolder = createViewHolder,
    bindViewHolder = bindViewHolder
)

inline fun <T, VH : RecyclerView.ViewHolder> linearListAdapter(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    layoutManager: LinearLayoutManager,
    horizontalMargin: Int = 0,
    verticalMargin: Int = 0,
    crossinline createViewHolder: (Context) -> VH,
    crossinline bindViewHolder: (viewHolder: VH, item: T) -> Unit
): LinearListAdapter<T, VH> = object : LinearListAdapter<T, VH>(itemDiffCallback, layoutManager) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createViewHolder(parent.context).also {
        it.itemView.layoutParams = layoutManager.fullSideListLayoutParams {
            this.horizontalMargin = horizontalMargin
            this.verticalMargin = verticalMargin
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolder(holder, getItem(position))
    }
}

@JvmName("verticalListAdapterWithViews")
inline fun <T, V : View> verticalListAdapter(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    horizontalMargin: Int = 0,
    verticalMargin: Int = 0,
    crossinline createView: (Context) -> V,
    reverseLayout: Boolean = false,
    crossinline bindViewHolder: BindViewHolder<V, T>
): LinearListAdapter<T, ViewHolder<V>> = linearListAdapter(
    itemDiffCallback = itemDiffCallback,
    layoutManager = verticalLayoutManager(reverseLayout),
    horizontalMargin = horizontalMargin,
    verticalMargin = verticalMargin,
    createView = createView,
    bindViewHolder = bindViewHolder
)

@JvmName("horizontalListAdapterWithViews")
inline fun <T, V : View> horizontalListAdapter(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    horizontalMargin: Int = 0,
    verticalMargin: Int = 0,
    crossinline createView: (Context) -> V,
    reverseLayout: Boolean = false,
    crossinline bindViewHolder: BindViewHolder<V, T>
): LinearListAdapter<T, ViewHolder<V>> = linearListAdapter(
    itemDiffCallback = itemDiffCallback,
    layoutManager = horizontalLayoutManager(reverseLayout),
    horizontalMargin = horizontalMargin,
    verticalMargin = verticalMargin,
    createView = createView,
    bindViewHolder = bindViewHolder
)

@JvmName("linearListAdapterWithViews")
inline fun <T, V : View> linearListAdapter(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    layoutManager: LinearLayoutManager,
    horizontalMargin: Int = 0,
    verticalMargin: Int = 0,
    crossinline createView: (Context) -> V,
    crossinline bindViewHolder: BindViewHolder<V, T>
): LinearListAdapter<T, ViewHolder<V>> = object : LinearListAdapter<T, ViewHolder<V>>(itemDiffCallback, layoutManager) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        return ViewHolder(createView(parent.context)).also {
            it.itemView.layoutParams = layoutManager.fullSideListLayoutParams {
                this.horizontalMargin = horizontalMargin
                this.verticalMargin = verticalMargin
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        bindViewHolder(holder, holder.itemView, getItem(position))
    }
}
