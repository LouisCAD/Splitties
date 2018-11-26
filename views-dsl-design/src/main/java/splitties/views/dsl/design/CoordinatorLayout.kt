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

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.Gravity
import android.view.View
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.wrapContent
import splitties.views.existingOrNewId
import android.support.design.widget.CoordinatorLayout.LayoutParams as LP

inline fun CoordinatorLayout.defaultLParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    gravity: Int = Gravity.NO_GRAVITY,
    initParams: LP.() -> Unit = {}
): LP = LP(width, height).also { it.gravity = gravity }.apply(initParams)

inline fun CoordinatorLayout.appBarLParams(
    height: Int = wrapContent,
    initParams: LP.() -> Unit = {}
): LP = defaultLParams(width = matchParent, height = height, initParams = initParams)

inline fun CoordinatorLayout.contentScrollingWithAppBarLParams(
    initParams: LP.() -> Unit = {}
): LP = defaultLParams(matchParent, matchParent) {
    behavior = AppBarLayout.ScrollingViewBehavior()
    initParams()
}

inline fun CoordinatorLayout.LayoutParams.anchorTo(view: View, gravity: Int = Gravity.NO_GRAVITY) {
    anchorId = view.existingOrNewId
    anchorGravity = gravity
}
