/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.constraintlayout

import androidx.constraintlayout.widget.ConstraintLayout
import splitties.views.dsl.core.matchParent
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * **A LESS CAPITALIZED ALIAS** to [ConstraintLayout.LayoutParams.MATCH_CONSTRAINT] that is only
 * visible inside [ConstraintLayout]s.
 */
@Suppress("unused")
inline val ConstraintLayout.matchConstraints
    get() = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT

inline fun ConstraintLayout.lParams(
    width: Int = matchConstraints,
    height: Int = matchConstraints,
    initParams: ConstraintLayout.LayoutParams.() -> Unit = {}
): ConstraintLayout.LayoutParams {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return createConstraintLayoutParams(width, height).apply(initParams).also { it.validate() }
}

@PublishedApi
internal fun ConstraintLayout.createConstraintLayoutParams(
    width: Int,
    height: Int
): ConstraintLayout.LayoutParams {
    val matchParentWidth = width == matchParent
    val matchParentHeight = height == matchParent
    val realWidth = if (matchParentWidth) matchConstraints else width
    val realHeight = if (matchParentHeight) matchConstraints else height
    return ConstraintLayout.LayoutParams(realWidth, realHeight).apply {
        if (matchParentWidth) centerHorizontally()
        if (matchParentHeight) centerVertically()
    }
}
