/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.material

import androidx.appcompat.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import splitties.resources.styledDimenPxSize
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.wrapContent
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams as LP

@Suppress("unused")
val CollapsingToolbarLayout.PIN
    get() = LP.COLLAPSE_MODE_PIN
@Suppress("unused")
val CollapsingToolbarLayout.PARALLAX
    get() = LP.COLLAPSE_MODE_PARALLAX

inline fun CollapsingToolbarLayout.defaultLParams(
    width: Int = matchParent,
    height: Int = wrapContent,
    initParams: LP.() -> Unit = {}
): LP = LP(width, height).apply(initParams)

inline fun CollapsingToolbarLayout.defaultLParams(
    width: Int = matchParent,
    height: Int = wrapContent,
    collapseMode: Int = LP.COLLAPSE_MODE_OFF,
    parallaxMultiplier: Float = 0.5f, // Default value as of 27.1.1
    initParams: LP.() -> Unit = {}
): LP {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return LP(width, height).also {
        it.collapseMode = collapseMode
        it.parallaxMultiplier = parallaxMultiplier
    }.apply(initParams)
}

inline fun CollapsingToolbarLayout.actionBarLParams(
    collapseMode: Int = LP.COLLAPSE_MODE_OFF,
    parallaxMultiplier: Float = 0.5f // Default value as of 27.1.1
): LP = LP(matchParent, styledDimenPxSize(R.attr.actionBarSize)).also {
    it.collapseMode = collapseMode
    it.parallaxMultiplier = parallaxMultiplier
}
