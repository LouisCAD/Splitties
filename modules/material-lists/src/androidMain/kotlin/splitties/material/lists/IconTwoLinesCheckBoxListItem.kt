/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.material.lists

import android.content.Context
import android.text.TextUtils.TruncateAt.END
import android.util.AttributeSet
import splitties.dimensions.dip
import splitties.resources.styledColorSL
import splitties.views.appcompat.imgTintList
import splitties.views.dsl.constraintlayout.*
import splitties.views.dsl.core.*
import splitties.views.selectable.constraintlayout.SelectableConstraintLayout
import splitties.views.textAppearance

class IconTwoLinesCheckBoxListItem(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0, disableDefaultTint: Boolean
) : SelectableConstraintLayout(context, attrs, defStyleAttr) {
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

    val firstLine = textView {
        ellipsize = END
        maxLines = 1
        textAppearance = R.style.TextAppearance_AppCompat_Subhead
    }

    val secondLine = textView {
        ellipsize = END
        maxLines = 1
        textAppearance = R.style.TextAppearance_AppCompat_Small
    }

    val checkBox = checkBox(R.id.checkbox)

    init {
        val iconSize = dip(24)
        add(icon, lParams(iconSize, iconSize) {
            verticalMargin = dip(8)
            startMargin = dip(16)
            startOfParent()
            centerVertically()
        })
        add(firstLine, lParams(height = wrapContent) {
            startMargin = dip(72)
            topMargin = dip(8)
            endMargin = dip(8)
            startOfParent()
            topOfParent()
            endToStartOf(checkBox)
        })
        add(secondLine, lParams(height = wrapContent) {
            endMargin = dip(8)
            bottomMargin = dip(8)
            startToStartOf(firstLine)
            topToBottomOf(firstLine)
            endToStartOf(checkBox)
            bottomOfParent()
        })
        add(checkBox, lParams(wrapContent, wrapContent) {
            endMargin = dip(16)
            endOfParent()
            centerVertically()
        })
    }
}
