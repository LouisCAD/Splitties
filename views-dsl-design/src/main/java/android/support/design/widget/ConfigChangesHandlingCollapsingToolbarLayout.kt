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
package android.support.design.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.support.design.R
import android.util.AttributeSet

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
            android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title
        )
    }
}
