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
package splitties.views.recyclerview

import android.content.Context
import android.support.annotation.IntegerRes
import android.support.v7.widget.GridLayoutManager
import splitties.resources.int

inline fun gridLayoutManager(
        spanCount: Int,
        reverseLayout: Boolean = false,
        setup: GridLayoutManager.() -> Unit = {}
) = GridLayoutManager(null, spanCount, GridLayoutManager.VERTICAL, reverseLayout).apply(setup)

inline fun gridLayoutManager(
        context: Context,
        @IntegerRes spanCountRes: Int,
        reverseLayout: Boolean = false,
        setup: GridLayoutManager.() -> Unit = {}
) = gridLayoutManager(context.int(spanCountRes), reverseLayout, setup)

inline fun horizontalGridLayoutManager(
        spanCount: Int,
        reverseLayout: Boolean = false,
        setup: GridLayoutManager.() -> Unit = {}
) = GridLayoutManager(null, spanCount, GridLayoutManager.HORIZONTAL, reverseLayout).apply(setup)

inline fun horizontalGridLayoutManager(
        context: Context,
        @IntegerRes spanCountRes: Int,
        reverseLayout: Boolean = false,
        setup: GridLayoutManager.() -> Unit = {}
) = horizontalGridLayoutManager(context.int(spanCountRes), reverseLayout, setup)
