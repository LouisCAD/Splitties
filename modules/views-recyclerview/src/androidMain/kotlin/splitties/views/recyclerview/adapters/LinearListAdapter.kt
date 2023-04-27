package splitties.views.recyclerview.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LinearListAdapter<T, VH : RecyclerView.ViewHolder>(
    itemDiffCallback: DiffUtil.ItemCallback<T>,
    override val layoutManager: LinearLayoutManager
) : ListAdapterWithLayoutManager<T, VH>(itemDiffCallback)
