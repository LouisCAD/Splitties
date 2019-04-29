/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.material.lists

import android.content.Context
import android.text.TextUtils.TruncateAt.END
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import splitties.dimensions.dip
import splitties.resources.styledColorSL
import splitties.views.appcompat.imgTintList
import splitties.views.dsl.core.*
import splitties.views.selectable.SelectableLinearLayout
import splitties.views.textAppearance

class IconOneLineListItem(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0, disableDefaultTint: Boolean
) : SelectableLinearLayout(context, attrs, defStyleAttr) {
    constructor(
        context: Context
    ) : this(context, null, disableDefaultTint = false)
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, disableDefaultTint = false)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : this(context, attrs, defStyleAttr, disableDefaultTint = false)
    constructor(
        context: Context,
        disableDefaultTint: Boolean
    ) : this(context, null, disableDefaultTint = disableDefaultTint)

    val icon = imageView {
        if (!disableDefaultTint) imgTintList = styledColorSL(android.R.attr.textColorSecondary)
    }

    /**
     * The one-line list item keeps the [firstLine] name for its only [TextView] to make
     * switching to and from [IconTwoLinesListItem] easier.
     */
    val firstLine = textView {
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
