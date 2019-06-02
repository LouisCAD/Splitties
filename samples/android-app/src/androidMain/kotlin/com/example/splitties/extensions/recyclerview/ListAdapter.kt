/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.recyclerview

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class ListAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    @Suppress("unused")
    protected constructor(diffCallback: DiffUtil.ItemCallback<T>? = null) {
        differ = diffCallback?.let {
            AsyncListDiffer(AdapterListUpdateCallback(this), AsyncDifferConfig.Builder(it).build())
        }
    }

    @Suppress("unused")
    protected constructor(config: AsyncDifferConfig<T>) {
        @Suppress("LeakingThis")
        differ = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    @Suppress("unused")
    protected constructor(list: List<T>) {
        this.list = list
        differ = null
    }

    /**
     * Protected, but open for subclasses to override in a public way, delegating to here.
     *
     * Submits a new list to be diffed, and displayed.
     *
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param list The new list to be displayed.
     */
    @CallSuper
    protected open fun submitList(list: List<T>?) {
        val differ = differ
        if (differ != null) {
            differ.submitList(list)
        } else {
            this.list = list
            notifyDataSetChanged()
        }
    }

    protected fun getItem(position: Int): T = currentList[position]

    final override fun getItemCount(): Int = currentList.size

    private val differ: AsyncListDiffer<T>?
    private var list: List<T>? = null

    protected val currentList: List<T> get() = differ?.currentList ?: list ?: emptyList()
}
