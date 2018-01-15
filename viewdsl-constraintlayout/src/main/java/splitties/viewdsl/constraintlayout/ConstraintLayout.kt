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

@file:Suppress("NOTHING_TO_INLINE")

package splitties.viewdsl.constraintlayout

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintLayout.LayoutParams.CHAIN_PACKED
import android.support.constraint.ConstraintLayout.LayoutParams.CHAIN_SPREAD
import android.support.constraint.ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.view.View
import splitties.viewdsl.core.matchParent

private typealias LP = ConstraintLayout.LayoutParams

const val MATCH_CONSTRAINT = LP.MATCH_CONSTRAINT
@Suppress("unused") inline val LP.parentId get() = PARENT_ID
@Suppress("unused") inline val LP.packed get() = CHAIN_PACKED
@Suppress("unused") inline val LP.spread get() = CHAIN_SPREAD
@Suppress("unused") inline val LP.spreadInside get() = CHAIN_SPREAD_INSIDE

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

inline fun LP.match(view: View) {
    val id = view.id
    topToTop = id
    leftToLeft = id
    bottomToBottom = id
    rightToRight = id
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


inline fun ConstraintLayout.lParams(
        width: Int = MATCH_CONSTRAINT,
        height: Int = MATCH_CONSTRAINT,
        initParams: LP.() -> Unit
): LP = createConstraintLayoutParams(width, height).also(initParams)

inline fun ConstraintLayout.lParams(
        width: Int = MATCH_CONSTRAINT,
        height: Int = MATCH_CONSTRAINT
): LP = createConstraintLayoutParams(width, height)

@PublishedApi
internal fun ConstraintLayout.createConstraintLayoutParams(width: Int, height: Int): LP {
    val matchParentWidth = width == matchParent
    val matchParentHeight = height == matchParent
    val realWidth = if (matchParentWidth) MATCH_CONSTRAINT else width
    val realHeight = if (matchParentHeight) MATCH_CONSTRAINT else height
    return LP(realWidth, realHeight).apply {
        if (matchParentWidth) {
            startToStart = parentId
            endToEnd = parentId
        }
        if (matchParentHeight) {
            topToTop = parentId
            bottomToBottom = parentId
        }
    }
}
