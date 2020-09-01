/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.preferences.ui

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R as MdcR
import splitties.dimensions.dip
import splitties.material.lists.IconTwoLinesCheckBoxListItem
import splitties.material.lists.IconTwoLinesListItem
import splitties.material.lists.IconTwoLinesSwitchListItem
import splitties.resources.styledColorSL
import splitties.typesaferecyclerview.ViewHolder
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.wrapContent
import splitties.views.gravityStartCenter
import splitties.views.horizontalPadding
import splitties.views.textAppearance

private const val maxLinesHardLimit = 100 // Hopefully, it's never close to be crossed.

class StaticHeaderViewHolder(
    context: Context
) : ViewHolder<TextView>(context.textView()) {

    init {
        itemView.apply {
            textAppearance = MdcR.style.TextAppearance_MaterialComponents_Subtitle1
            setTextColor(styledColorSL(MdcR.attr.colorAccent))

            gravity = gravityStartCenter
            horizontalPadding = dip(16)
            minHeight = dip(48)
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }
    }

    fun bind(item: StaticHeader) {
        itemView.text = item.headerText
    }
}

class IconTwoLinesViewHolder(
    context: Context
) : ViewHolder<IconTwoLinesListItem>(IconTwoLinesListItem(context)) {

    init {
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }
    }

    fun bind(prefItem: IconTwoLinesItem) {
        itemView.isEnabled = prefItem.isEnabled()
        itemView.firstLine.text = prefItem.firstLineText()
        itemView.secondLine.text = prefItem.secondLineText()
        itemView.icon.setImageDrawable(prefItem.getIconDrawable())
        itemView.setOnClickListener(null)
        boundItem = prefItem
        itemView.setOnClickListener(clickListener)
    }

    private lateinit var boundItem: IconTwoLinesItem

    private val clickListener = View.OnClickListener {
        boundItem.onClick()
    }
}

class BoolPrefIconTwoTextCheckBoxViewHolder(
    context: Context
) : ViewHolder<IconTwoLinesCheckBoxListItem>(IconTwoLinesCheckBoxListItem(context)) {

    init {
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }
    }

    fun bind(prefItem: BoolPrefCheckBoxItem) {
        val firstTextView = itemView.firstLine
        val secondTextView = itemView.secondLine
        when (prefItem.presentation) {
            BoolPrefItem.Presentation.CheckBox.TwoLines -> {
                if (SDK_INT < 16 || firstTextView.maxLines != 1) firstTextView.maxLines = 1
                if (SDK_INT < 16 || secondTextView.maxLines != 1) secondTextView.maxLines = 1
            }
            BoolPrefItem.Presentation.CheckBox.TwoText -> {
                if (SDK_INT < 16 || firstTextView.maxLines == 1) {
                    firstTextView.maxLines = maxLinesHardLimit
                }
                if (SDK_INT < 16 || secondTextView.maxLines == 1) {
                    secondTextView.maxLines = maxLinesHardLimit
                }
            }
        }
        val value = prefItem.delegate.value
        itemView.isEnabled = prefItem.isEnabled(value)
        itemView.firstLine.text = prefItem.firstLineText(value)
        itemView.secondLine.text = prefItem.secondLineText(value)
        itemView.icon.setImageDrawable(prefItem.getIconDrawable(value))
        itemView.checkBox.setOnCheckedChangeListener(null)
        itemView.checkBox.isChecked = value
        boundItem = prefItem
        itemView.checkBox.setOnCheckedChangeListener(listener)
        itemView.setOnClickListener(listener)
    }

    private lateinit var boundItem: BoolPrefCheckBoxItem

    private val listener = object : CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            boundItem.delegate.value = isChecked
        }

        override fun onClick(v: View?) {
            boundItem.delegate.apply { value = value.not() }
        }
    }
}

class BoolPrefIconTwoTextSwitchViewHolder(
    context: Context
) : ViewHolder<IconTwoLinesSwitchListItem>(IconTwoLinesSwitchListItem(context)) {

    init {
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(matchParent, wrapContent)
        }
    }

    fun bind(prefItem: BoolPrefSwitchItem) {
        val firstTextView = itemView.firstLine
        val secondTextView = itemView.secondLine
        when (prefItem.presentation) {
            BoolPrefItem.Presentation.Switch.TwoLines -> {
                if (SDK_INT < 16 || firstTextView.maxLines != 1) firstTextView.maxLines = 1
                if (SDK_INT < 16 || secondTextView.maxLines != 1) secondTextView.maxLines = 1
            }
            BoolPrefItem.Presentation.Switch.TwoText -> {
                if (SDK_INT < 16 || firstTextView.maxLines == 1) {
                    firstTextView.maxLines = maxLinesHardLimit
                }
                if (SDK_INT < 16 || secondTextView.maxLines == 1) {
                    secondTextView.maxLines = maxLinesHardLimit
                }
            }
        }
        val value = prefItem.delegate.value
        itemView.isEnabled = prefItem.isEnabled(value)
        itemView.firstLine.text = prefItem.firstLineText(value)
        itemView.secondLine.text = prefItem.secondLineText(value)
        itemView.icon.setImageDrawable(prefItem.getIconDrawable(value))
        itemView.switch.setOnCheckedChangeListener(null)
        itemView.switch.isChecked = value
        boundItem = prefItem
        itemView.switch.setOnCheckedChangeListener(listener)
        itemView.setOnClickListener(listener)
    }

    private lateinit var boundItem: BoolPrefSwitchItem

    private val listener = object : CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            boundItem.delegate.value = isChecked
        }

        override fun onClick(v: View?) {
            boundItem.delegate.apply { value = value.not() }
        }
    }
}
