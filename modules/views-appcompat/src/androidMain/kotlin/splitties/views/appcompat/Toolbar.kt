/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.appcompat

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import androidx.appcompat.R
import splitties.resources.styledDimenPxSize
import kotlin.LazyThreadSafetyMode.NONE
import androidx.appcompat.widget.Toolbar as AndroidXToolbar

/**
 * An [AndroidXToolbar] that handles configuration changes and changes its size accordingly.
 */
class Toolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = R.attr.toolbarStyle
) : AndroidXToolbar(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)

    private val maxBtHeightField by lazy(NONE) {
        AndroidXToolbar::class.java.getDeclaredField("mMaxButtonHeight").apply {
            isAccessible = true
        }
    }

    @SuppressLint("PrivateResource")
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        setTitleTextAppearance(context, R.style.TextAppearance_Widget_AppCompat_Toolbar_Title)
        setSubtitleTextAppearance(context, R.style.TextAppearance_Widget_AppCompat_Toolbar_Subtitle)
        val actionBarSize = styledDimenPxSize(R.attr.actionBarSize)
        minimumHeight = actionBarSize
        maxBtHeightField.set(this, actionBarSize)
    }
}
