/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.constraintlayout

import android.widget.LinearLayout
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import splitties.dimensions.dip
import splitties.views.dsl.core.add
import splitties.views.dsl.core.view
import splitties.views.dsl.core.wrapContent
import splitties.views.generateViewId

/**
 * Creates and adds a vertical [Guideline] to this [ConstraintLayout].
 * A value needs to be supplied for one and only one of the three parameters.
 * [begin] and [end] are in pixels (you can use [dip]).
 * [widthRatio] is a value between 0 and 1.
 */
fun ConstraintLayout.verticalGuideline(
    @Px begin: Int = -1,
    @Px end: Int = -1,
    widthRatio: Float = -1.0f
): Guideline = guideline(begin, end, widthRatio, vertical = true)

/**
 * Creates and adds a horizontal [Guideline] to this [ConstraintLayout].
 * A value needs to be supplied for one and only one of the three parameters.
 * [begin] and [end] are in pixels (you can use [dip]).
 * [heightRatio] is a value between 0 and 1.
 */
fun ConstraintLayout.horizontalGuideline(
    @Px begin: Int = -1,
    @Px end: Int = -1,
    heightRatio: Float = -1.0f
): Guideline = guideline(begin, end, heightRatio, vertical = false)

private fun ConstraintLayout.guideline(
    begin: Int,
    end: Int,
    sideRatio: Float,
    vertical: Boolean
): Guideline {
    require(when {
        begin != -1 -> end == -1 && sideRatio == -1.0f
        end != -1 -> begin == -1 && sideRatio == -1.0f
        sideRatio != -1f -> begin == -1 && end == -1 && sideRatio >= 0f && sideRatio <= 1f
        else -> false
    })
    return add(view(::Guideline) {
        id = generateViewId()
    }, lParams(wrapContent, wrapContent) {
        orientation = if (vertical) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
        guideBegin = begin
        guideEnd = end
        guidePercent = sideRatio
    })
}
