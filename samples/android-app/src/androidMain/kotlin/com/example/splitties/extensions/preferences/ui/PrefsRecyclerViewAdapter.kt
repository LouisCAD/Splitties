/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.preferences.ui

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class PrefsRecyclerViewAdapter : AbstractPrefsRecyclerViewAdapter {

    constructor(diffCallback: DiffUtil.ItemCallback<Item>? = null) : super(diffCallback)
    constructor(config: AsyncDifferConfig<Item>) : super(config)
    constructor(list: List<Item>) : super(list)

    @IntRange(from = 0L)
    open fun getItemViewTypeOfOtherItem(content: Any?): Int {
        throw UnsupportedOperationException("Override this function to handle other items.")
    }

    open fun createOtherItemViewHolder(
        @IntRange(from = 0L) viewType: Int
    ): RecyclerView.ViewHolder {
        throw UnsupportedOperationException("Override this function to handle other items.")
    }

    open fun bindOtherItem(content: Any?) {
        throw UnsupportedOperationException("Override this function to handle other items.")
    }

    private object BuiltInViewTypes {
        const val StaticHeader = -1
        const val IconTwoLinesItem = -2
        const val BoolPrefItemCheckBox = -3
        const val BoolPrefItemSwitch = -4
    }

    final override fun getItemViewType(position: Int): Int = when (val item = getItem(position)) {
        is StaticHeader -> BuiltInViewTypes.StaticHeader
        is IconTwoLinesItem -> BuiltInViewTypes.IconTwoLinesItem
        is BoolPrefItem<*> -> when (item.presentation) {
            is BoolPrefItem.Presentation.CheckBox -> BuiltInViewTypes.BoolPrefItemCheckBox
            is BoolPrefItem.Presentation.Switch -> BuiltInViewTypes.BoolPrefItemSwitch
        }
        is OtherItem<*> -> getItemViewTypeOfOtherItem(item.content).also {
            check(it >= 0) { "Other items viewType must be positive." }
        }
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        BuiltInViewTypes.StaticHeader -> StaticHeaderViewHolder(parent.context)
        BuiltInViewTypes.IconTwoLinesItem -> IconTwoLinesViewHolder(parent.context)
        BuiltInViewTypes.BoolPrefItemCheckBox -> BoolPrefIconTwoTextCheckBoxViewHolder(parent.context)
        BuiltInViewTypes.BoolPrefItemSwitch -> BoolPrefIconTwoTextSwitchViewHolder(parent.context)
        else -> createOtherItemViewHolder(viewType)
    }

    final override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ): Unit = when (val item = getItem(position)) {
        is StaticHeader -> (holder as StaticHeaderViewHolder).bind(item)
        is IconTwoLinesItem -> (holder as IconTwoLinesViewHolder).bind(item)
        is BoolPrefItem<*> -> when (item.presentation) {
            is BoolPrefItem.Presentation.CheckBox -> {
                @Suppress("UNCHECKED_CAST")
                (holder as BoolPrefIconTwoTextCheckBoxViewHolder).bind(item as BoolPrefCheckBoxItem)
            }
            is BoolPrefItem.Presentation.Switch -> {
                @Suppress("UNCHECKED_CAST")
                (holder as BoolPrefIconTwoTextSwitchViewHolder).bind(item as BoolPrefSwitchItem)
            }
        }
        is OtherItem<*> -> bindOtherItem(item.content)
    }
}
