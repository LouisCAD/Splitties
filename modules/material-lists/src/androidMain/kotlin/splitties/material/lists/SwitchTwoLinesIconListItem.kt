/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.material.lists

import android.content.Context
import android.text.TextUtils.TruncateAt.END
import androidx.appcompat.widget.SwitchCompat
import splitties.dimensions.dip
import splitties.resources.styledColorSL
import splitties.views.appcompat.imgTintList
import splitties.views.dsl.constraintlayout.*
import splitties.views.dsl.core.*
import splitties.views.selectable.constraintlayout.SelectableConstraintLayout
import splitties.views.textAppearance

class SwitchTwoLinesIconListItem(
    context: Context,
    disableDefaultTint: Boolean
) : SelectableConstraintLayout(context) {
    constructor(context: Context) : this(context, disableDefaultTint = false)

    val switch = view(::SwitchCompat, R.id.toggle)

    val icon = imageView(R.id.icon) {
        if (!disableDefaultTint) imgTintList = styledColorSL(android.R.attr.textColorSecondary)
    }

    val firstLine = textView(R.id.firstLine) {
        ellipsize = END
        maxLines = 1
        textAppearance = R.style.TextAppearance_AppCompat_Subhead
    }

    val secondLine = textView(R.id.secondLine) {
        ellipsize = END
        maxLines = 1
        textAppearance = R.style.TextAppearance_AppCompat_Small
    }

    init {
        add(switch, lParams(wrapContent, wrapContent) {
            startMargin = dip(16)
            startOfParent()
            centerVertically()
        })
        add(firstLine, lParams(height = wrapContent) {
            startMargin = dip(72)
            topMargin = dip(8)
            endMargin = dip(16)
            startOfParent()
            topOfParent()
            endToStartOf(icon)
        })
        add(secondLine, lParams(height = wrapContent) {
            verticalMargin = dip(8)
            endMargin = dip(8)
            startToStartOf(firstLine)
            topToBottomOf(firstLine)
            endToStartOf(icon)
            bottomOfParent()
        })
        val iconSize = dip(24)
        add(icon, lParams(iconSize, iconSize) {
            verticalMargin = dip(8)
            endMargin = dip(16)
            centerVertically()
            endOfParent()
        })
    }
}
