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

package splitties.material.lists

import android.content.Context
import android.text.TextUtils.TruncateAt.END
import android.view.Gravity
import android.widget.TextView
import splitties.dimensions.dip
import splitties.resources.styledColorSL
import splitties.views.appcompat.imgTintList
import splitties.views.dsl.core.*
import splitties.views.selectable.SelectableLinearLayout
import splitties.views.textAppearance

class IconOneLineListItem(
    context: Context,
    disableDefaultTint: Boolean
) : SelectableLinearLayout(context) {
    constructor(context: Context) : this(context, disableDefaultTint = false)

    val icon = imageView(R.id.icon) {
        if (!disableDefaultTint) imgTintList = styledColorSL(android.R.attr.textColorSecondary)
    }

    /**
     * The one-line list item keeps the [firstLine] name for its only [TextView] to make
     * switching to and from [IconTwoLinesListItem] easier.
     */
    val firstLine = textView(R.id.firstLine) {
        ellipsize = END
        maxLines = 1
        textAppearance = R.style.TextAppearance_AppCompat_Subhead
    }

    init {
        val iconSize = dip(24)
        add(icon, lParams(iconSize, iconSize) {
            gravity = Gravity.CENTER_VERTICAL or Gravity.START
            startMargin = dip(16)
        })
        add(firstLine, lParams(height = wrapContent) {
            gravity = Gravity.CENTER_VERTICAL or Gravity.START
            startMargin = dip(32)
            verticalMargin = dip(16)
            endMargin = dip(16)
        })
    }
}
