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

import android.view.View
import android.support.constraint.ConstraintLayout.LayoutParams as LP

@Suppress("unused") inline val LP.parentId get() = LP.PARENT_ID
@Suppress("unused") inline val LP.packed get() = LP.CHAIN_PACKED
@Suppress("unused") inline val LP.spread get() = LP.CHAIN_SPREAD
@Suppress("unused") inline val LP.spreadInside get() = LP.CHAIN_SPREAD_INSIDE

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

@PublishedApi internal val View.idOrThrow: Int
    get() = id.also { check(it != View.NO_ID) { "This view has no id!" } }

inline fun LP.centerOn(view: View) {
    val id = view.idOrThrow
    topToTop = id
    leftToLeft = id
    bottomToBottom = id
    rightToRight = id
}

inline fun LP.topToTopOf(view: View) {
    topToTop = view.idOrThrow
}

inline fun LP.topToBottomOf(view: View) {
    topToBottom = view.idOrThrow
}

inline fun LP.bottomToTopOf(view: View) {
    bottomToTop = view.idOrThrow
}

inline fun LP.bottomToBottomOf(view: View) {
    bottomToBottom = view.idOrThrow
}

inline fun LP.baselineToBaselineOf(view: View) {
    baselineToBaseline = view.idOrThrow
}

inline fun LP.startToStartOf(view: View) {
    startToStart = view.idOrThrow
}

inline fun LP.startToEndOf(view: View) {
    startToEnd = view.idOrThrow
}

inline fun LP.endToStartOf(view: View) {
    endToStart = view.idOrThrow
}

inline fun LP.endToEndOf(view: View) {
    endToEnd = view.idOrThrow
}

inline fun LP.leftToLeftOf(view: View) {
    leftToLeft = view.idOrThrow
}

inline fun LP.leftToRightOf(view: View) {
    leftToRight = view.idOrThrow
}

inline fun LP.rightToRightOf(view: View) {
    rightToRight = view.idOrThrow
}

inline fun LP.rightToLeftOf(view: View) {
    rightToLeft = view.idOrThrow
}

inline fun LP.alignVerticallyOn(view: View) {
    val id = view.idOrThrow
    topToTop = id
    bottomToBottom = id
}

inline fun LP.alignHorizontallyOn(view: View) {
    val id = view.idOrThrow
    leftToLeft = id
    rightToRight = id
}
