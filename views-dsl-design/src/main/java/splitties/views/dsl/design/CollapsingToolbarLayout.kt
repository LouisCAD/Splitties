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

package splitties.views.dsl.design

import android.support.design.widget.CollapsingToolbarLayout
import splitties.resources.styledDimenPxSize
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.wrapContent
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams as LP

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
): LP = LP(width, height).also {
    it.collapseMode = collapseMode
    it.parallaxMultiplier = parallaxMultiplier
}.apply(initParams)

inline fun CollapsingToolbarLayout.actionBarLParams(
    collapseMode: Int = LP.COLLAPSE_MODE_OFF,
    parallaxMultiplier: Float = 0.5f // Default value as of 27.1.1
): LP = LP(matchParent, styledDimenPxSize(R.attr.actionBarSize)).also {
    it.collapseMode = collapseMode
    it.parallaxMultiplier = parallaxMultiplier
}
