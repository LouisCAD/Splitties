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
import splitties.viewdsl.core.matchParent

@PublishedApi internal const val MATCH_CONSTRAINT = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT

inline fun ConstraintLayout.lParams(
        width: Int = MATCH_CONSTRAINT,
        height: Int = MATCH_CONSTRAINT,
        initParams: ConstraintLayout.LayoutParams.() -> Unit
): ConstraintLayout.LayoutParams = createConstraintLayoutParams(width, height).also(initParams)

inline fun ConstraintLayout.lParams(
        width: Int = MATCH_CONSTRAINT,
        height: Int = MATCH_CONSTRAINT
): ConstraintLayout.LayoutParams = createConstraintLayoutParams(width, height)

@PublishedApi internal fun ConstraintLayout.createConstraintLayoutParams(
        width: Int,
        height: Int
): ConstraintLayout.LayoutParams {
    val matchParentWidth = width == matchParent
    val matchParentHeight = height == matchParent
    val realWidth = if (matchParentWidth) MATCH_CONSTRAINT else width
    val realHeight = if (matchParentHeight) MATCH_CONSTRAINT else height
    return ConstraintLayout.LayoutParams(realWidth, realHeight).apply {
        if (matchParentWidth) centerHorizontally()
        if (matchParentHeight) centerVertically()
    }
}
