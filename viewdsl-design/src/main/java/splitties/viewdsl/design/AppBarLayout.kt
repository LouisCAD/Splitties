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

inline fun AppBarLayout.defaultLParams(
        width: Int = matchParent,
        height: Int = wrapContent,
        initParams: AppBarLayout.LayoutParams.() -> Unit
) = AppBarLayout.LayoutParams(width, height).apply(initParams)

inline fun AppBarLayout.defaultLParams(
        width: Int = matchParent,
        height: Int = wrapContent
) = AppBarLayout.LayoutParams(width, height)
