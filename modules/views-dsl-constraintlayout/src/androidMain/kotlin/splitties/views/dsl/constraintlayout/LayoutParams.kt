/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.constraintlayout

import android.view.View
import androidx.annotation.Px
import splitties.views.*
import splitties.views.dsl.core.*
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams as LP

inline val @Suppress("unused") LP.parentId
    get() = LP.PARENT_ID


inline val @Suppress("unused") LP.packed
    get() = LP.CHAIN_PACKED

inline val @Suppress("unused") LP.spread
    get() = LP.CHAIN_SPREAD

inline val @Suppress("unused") LP.spreadInside
    get() = LP.CHAIN_SPREAD_INSIDE


inline fun LP.centerHorizontally() {
    startToStart = parentId
    endToEnd = parentId
}

inline fun LP.centerHorizontally(@Px margin: Int) {
    startToStart = parentId
    endToEnd = parentId
    horizontalMargin = margin
}

inline fun LP.centerVertically() {
    topToTop = parentId
    bottomToBottom = parentId
}

inline fun LP.centerVertically(@Px margin: Int) {
    topToTop = parentId
    bottomToBottom = parentId
    verticalMargin = margin
}


inline fun LP.centerInParent() {
    centerHorizontally()
    centerVertically()
}

inline fun LP.centerInParent(@Px margin: Int) {
    centerHorizontally(margin = margin)
    centerVertically(margin = margin)
}

inline fun LP.centerInParent(@Px horizontalMargin: Int, @Px verticalMargin: Int) {
    centerHorizontally(margin = horizontalMargin)
    centerVertically(margin = verticalMargin)
}


inline fun LP.topOfParent(@Px margin: Int = topMargin) {
    topToTop = parentId
    topMargin = margin
}

inline fun LP.bottomOfParent(@Px margin: Int = bottomMargin) {
    bottomToBottom = parentId
    bottomMargin = margin
}

inline fun LP.startOfParent(@Px margin: Int = startMargin) {
    startToStart = parentId
    startMargin = margin
}

inline fun LP.endOfParent(@Px margin: Int = endMargin) {
    endToEnd = parentId
    endMargin = margin
}

inline fun LP.leftOfParent(@Px margin: Int = leftMargin) {
    leftToLeft = parentId
    leftMargin = margin
}

inline fun LP.rightOfParent(@Px margin: Int = rightMargin) {
    rightToRight = parentId
    rightMargin = margin
}


inline fun LP.centerOn(view: View) {
    val id = view.existingOrNewId
    topToTop = id
    leftToLeft = id
    bottomToBottom = id
    rightToRight = id
}

inline fun LP.centerOn(view: View, @Px margin: Int) {
    val id = view.existingOrNewId
    topToTop = id
    leftToLeft = id
    bottomToBottom = id
    rightToRight = id
    this.margin = margin
}

inline fun LP.centerOn(
    view: View,
    @Px horizontalMargin: Int,
    @Px verticalMargin: Int
) {
    val id = view.existingOrNewId
    topToTop = id
    leftToLeft = id
    bottomToBottom = id
    rightToRight = id
    this.horizontalMargin = horizontalMargin
    this.verticalMargin = verticalMargin
}


inline fun LP.before(
    view: View,
    @Px margin: Int = endMargin,
    @Px goneMargin: Int = goneEndMargin
) = endToStartOf(view, margin, goneMargin)

inline fun LP.above(
    view: View,
    @Px margin: Int = bottomMargin,
    @Px goneMargin: Int = goneBottomMargin
) = bottomToTopOf(view, margin, goneMargin)

inline fun LP.after(
    view: View,
    @Px margin: Int = startMargin,
    @Px goneMargin: Int = goneStartMargin
) = startToEndOf(view, margin, goneMargin)

inline fun LP.below(
    view: View,
    @Px margin: Int = topMargin,
    @Px goneMargin: Int = goneTopMargin
) = topToBottomOf(view, margin, goneMargin)


inline fun LP.topToTopOf(
    view: View,
    @Px margin: Int = topMargin,
    @Px goneMargin: Int = goneTopMargin
) {
    topToTop = view.existingOrNewId
    topMargin = margin
    goneTopMargin = goneMargin
}

inline fun LP.topToBottomOf(
    view: View,
    @Px margin: Int = topMargin,
    @Px goneMargin: Int = goneTopMargin
) {
    topToBottom = view.existingOrNewId
    topMargin = margin
    goneTopMargin = goneMargin
}

inline fun LP.bottomToTopOf(
    view: View,
    @Px margin: Int = bottomMargin,
    @Px goneMargin: Int = goneBottomMargin
) {
    bottomToTop = view.existingOrNewId
    bottomMargin = margin
    goneBottomMargin = goneMargin
}

inline fun LP.bottomToBottomOf(
    view: View,
    @Px margin: Int = bottomMargin,
    @Px goneMargin: Int = goneBottomMargin
) {
    bottomToBottom = view.existingOrNewId
    bottomMargin = margin
    goneBottomMargin = goneMargin
}

inline fun LP.baselineToBaselineOf(view: View) {
    baselineToBaseline = view.existingOrNewId
}

inline fun LP.startToStartOf(
    view: View,
    @Px margin: Int = startMargin,
    @Px goneMargin: Int = goneStartMargin
) {
    startToStart = view.existingOrNewId
    startMargin = margin
    goneStartMargin = goneMargin
}

inline fun LP.startToEndOf(
    view: View,
    @Px margin: Int = startMargin,
    @Px goneMargin: Int = goneStartMargin
) {
    startToEnd = view.existingOrNewId
    startMargin = margin
    goneStartMargin = goneMargin
}

inline fun LP.endToStartOf(
    view: View,
    @Px margin: Int = endMargin,
    @Px goneMargin: Int = goneEndMargin
) {
    endToStart = view.existingOrNewId
    endMargin = margin
    goneEndMargin = goneMargin
}

inline fun LP.endToEndOf(
    view: View,
    @Px margin: Int = endMargin,
    @Px goneMargin: Int = goneEndMargin
) {
    endToEnd = view.existingOrNewId
    endMargin = margin
    goneEndMargin = goneMargin
}

inline fun LP.leftToLeftOf(
    view: View,
    @Px margin: Int = leftMargin,
    @Px goneMargin: Int = goneLeftMargin
) {
    leftToLeft = view.existingOrNewId
    leftMargin = margin
    goneLeftMargin = goneMargin
}

inline fun LP.leftToRightOf(
    view: View,
    @Px margin: Int = leftMargin,
    @Px goneMargin: Int = goneLeftMargin
) {
    leftToRight = view.existingOrNewId
    leftMargin = margin
    goneLeftMargin = goneMargin
}

inline fun LP.rightToRightOf(
    view: View,
    @Px margin: Int = rightMargin,
    @Px goneMargin: Int = goneRightMargin
) {
    rightToRight = view.existingOrNewId
    rightMargin = margin
    goneRightMargin = goneMargin
}

inline fun LP.rightToLeftOf(
    view: View,
    @Px margin: Int = rightMargin,
    @Px goneMargin: Int = goneRightMargin
) {
    rightToLeft = view.existingOrNewId
    rightMargin = margin
    goneRightMargin = goneMargin
}


inline fun LP.alignVerticallyOn(view: View) {
    val id = view.existingOrNewId
    topToTop = id
    bottomToBottom = id
}

inline fun LP.alignHorizontallyOn(view: View) {
    val id = view.existingOrNewId
    leftToLeft = id
    rightToRight = id
}

inline fun LP.alignVerticallyOn(view: View, @Px margin: Int) {
    val id = view.existingOrNewId
    topToTop = id
    bottomToBottom = id
    verticalMargin = margin
}

inline fun LP.alignHorizontallyOn(view: View, @Px margin: Int) {
    val id = view.existingOrNewId
    leftToLeft = id
    rightToRight = id
    horizontalMargin = margin
}
