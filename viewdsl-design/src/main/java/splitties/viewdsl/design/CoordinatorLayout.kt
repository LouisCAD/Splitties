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

package splitties.viewdsl.design

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.wrapContent
import android.support.design.widget.CoordinatorLayout.LayoutParams as LP

inline fun CoordinatorLayout.defaultLParams(
        width: Int = wrapContent,
        height: Int = wrapContent,
        gravity: Int = Gravity.NO_GRAVITY,
        initParams: LP.() -> Unit
) = LP(width, height).also { it.gravity = gravity }.apply(initParams)

inline fun CoordinatorLayout.defaultLParams(
        width: Int = wrapContent,
        height: Int = wrapContent,
        gravity: Int = Gravity.NO_GRAVITY
) = LP(width, height).also { it.gravity = gravity }

inline fun CoordinatorLayout.defaultLParams(
        width: Int = wrapContent,
        height: Int = wrapContent
) = LP(width, height)

inline fun CoordinatorLayout.appBarLParams(
        height: Int = wrapContent,
        initParams: LP.() -> Unit = {}
) = defaultLParams(width = matchParent, height = height, initParams = initParams)

@Suppress("unused")
fun CoordinatorLayout.contentScrollingWithAppBarLParams() = scrollingContentLParams

private val scrollingContentLParams = LP(MATCH_PARENT, MATCH_PARENT).also {
    it.behavior = AppBarLayout.ScrollingViewBehavior()
}
