package splitties.views.recyclerview.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class ListAdapterWithLayoutManager<T, VH : RecyclerView.ViewHolder>(
    itemDiffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(itemDiffCallback) {
    abstract val layoutManager: RecyclerView.LayoutManager
}
