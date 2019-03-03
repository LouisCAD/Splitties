/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.constraintlayout

import android.view.View
import splitties.views.existingOrNewId
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams as LP

@Suppress("unused")
inline val LP.parentId
    get() = LP.PARENT_ID
@Suppress("unused")
inline val LP.packed
    get() = LP.CHAIN_PACKED
@Suppress("unused")
inline val LP.spread
    get() = LP.CHAIN_SPREAD
@Suppress("unused")
inline val LP.spreadInside
    get() = LP.CHAIN_SPREAD_INSIDE

inline fun LP.centerHorizontally() {
    startToStart = parentId
    endToEnd = parentId
}

inline fun LP.centerVertically() {
    topToTop = parentId
    bottomToBottom = parentId
}

inline fun LP.centerInParent() {
    centerHorizontally()
    centerVertically()
}

inline fun LP.topOfParent() {
    topToTop = parentId
}

inline fun LP.bottomOfParent() {
    bottomToBottom = parentId
}

inline fun LP.startOfParent() {
    startToStart = parentId
}

inline fun LP.endOfParent() {
    endToEnd = parentId
}

inline fun LP.leftOfParent() {
    leftToLeft = parentId
}

inline fun LP.rightOfParent() {
    rightToRight = parentId
}

inline fun LP.centerOn(view: View) {
    val id = view.existingOrNewId
    topToTop = id
    leftToLeft = id
    bottomToBottom = id
    rightToRight = id
}

inline fun LP.topToTopOf(view: View) {
    topToTop = view.existingOrNewId
}

inline fun LP.topToBottomOf(view: View) {
    topToBottom = view.existingOrNewId
}

inline fun LP.bottomToTopOf(view: View) {
    bottomToTop = view.existingOrNewId
}

inline fun LP.bottomToBottomOf(view: View) {
    bottomToBottom = view.existingOrNewId
}

inline fun LP.baselineToBaselineOf(view: View) {
    baselineToBaseline = view.existingOrNewId
}

inline fun LP.startToStartOf(view: View) {
    startToStart = view.existingOrNewId
}

inline fun LP.startToEndOf(view: View) {
    startToEnd = view.existingOrNewId
}

inline fun LP.endToStartOf(view: View) {
    endToStart = view.existingOrNewId
}

inline fun LP.endToEndOf(view: View) {
    endToEnd = view.existingOrNewId
}

inline fun LP.leftToLeftOf(view: View) {
    leftToLeft = view.existingOrNewId
}

inline fun LP.leftToRightOf(view: View) {
    leftToRight = view.existingOrNewId
}

inline fun LP.rightToRightOf(view: View) {
    rightToRight = view.existingOrNewId
}

inline fun LP.rightToLeftOf(view: View) {
    rightToLeft = view.existingOrNewId
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
