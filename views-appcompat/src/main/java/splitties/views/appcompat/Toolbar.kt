/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.views.appcompat

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import splitties.resources.styledDimenPxSize
import kotlin.LazyThreadSafetyMode.NONE
import android.support.v7.widget.Toolbar as SupportToolbar

/**
 * A [SupportToolbar] that handles configuration changes and changes its size accordingly.
 */
class Toolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = R.attr.toolbarStyle
) : SupportToolbar(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)

    private val maxBtHeightField by lazy(NONE) {
        SupportToolbar::class.java.getDeclaredField("mMaxButtonHeight").apply {
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
