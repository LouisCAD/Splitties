/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.google.android.material.appbar

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import com.google.android.material.R

/**
 * A [CollapsingToolbarLayout] that handles configuration changes and changes its size accordingly.
 */
@PublishedApi
internal class ConfigChangesHandlingCollapsingToolbarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : CollapsingToolbarLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)

    @SuppressLint("RestrictedApi")
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        collapsingTextHelper.setExpandedTextAppearance(
            R.style.TextAppearance_Design_CollapsingToolbar_Expanded
        )
        collapsingTextHelper.setCollapsedTextAppearance(
            androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title
        )
    }
}
