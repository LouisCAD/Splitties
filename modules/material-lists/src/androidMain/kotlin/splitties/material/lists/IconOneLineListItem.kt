/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.material.lists

import android.content.Context
import android.text.TextUtils.TruncateAt.END
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.R
import splitties.dimensions.dip
import splitties.resources.styledColorSL
import splitties.views.appcompat.imgTintList
import splitties.views.dsl.core.add
import splitties.views.endMargin
import splitties.views.dsl.core.imageView
import splitties.views.dsl.core.lParams
import splitties.views.startMargin
import splitties.views.dsl.core.textView
import splitties.views.verticalMargin
import splitties.views.dsl.core.wrapContent
import splitties.views.selectable.SelectableLinearLayout
import splitties.views.textAppearance

class IconOneLineListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    disableDefaultTint: Boolean = false
) : SelectableLinearLayout(context, attrs, defStyleAttr) {

    val icon = imageView {
        if (!disableDefaultTint) imgTintList = styledColorSL(android.R.attr.textColorSecondary)
        isDuplicateParentStateEnabled = true
    }

    /**
     * The one-line list item keeps the [firstLine] name for its only [TextView] to make
     * switching to and from [IconTwoLinesListItem] easier.
     */
    val firstLine = textView {
        ellipsize = END
        minLines = 1
        maxLines = 1
        textAppearance = R.style.TextAppearance_AppCompat_Subhead
        isDuplicateParentStateEnabled = true
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
