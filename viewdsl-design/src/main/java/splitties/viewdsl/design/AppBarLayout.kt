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
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.wrapContent
import android.support.design.widget.AppBarLayout.LayoutParams as LP

@Suppress("unused") val AppBarLayout.SCROLL get() = LP.SCROLL_FLAG_SCROLL
@Suppress("unused") val AppBarLayout.EXIT_UNTIL_COLLAPSED
    get() = LP.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
@Suppress("unused") val AppBarLayout.ENTER_ALWAYS get() = LP.SCROLL_FLAG_ENTER_ALWAYS
@Suppress("unused") val AppBarLayout.ENTER_ALWAYS_COLLAPSED: Int
    get() = LP.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
@Suppress("unused") val AppBarLayout.SNAP get() = LP.SCROLL_FLAG_SNAP

inline fun AppBarLayout.defaultLParams(
        width: Int = matchParent,
        height: Int = wrapContent,
        scrollFlags: Int = ENTER_ALWAYS,
        initParams: LP.() -> Unit
) = LP(width, height).also { it.scrollFlags = scrollFlags }.apply(initParams)

inline fun AppBarLayout.defaultLParams(
        width: Int = matchParent,
        height: Int = wrapContent,
        scrollFlags: Int = ENTER_ALWAYS
) = LP(width, height).also { it.scrollFlags = scrollFlags }
