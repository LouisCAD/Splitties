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

package splitties.viewdsl.recyclerview

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import splitties.viewdsl.core.NO_THEME
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.inflate
import splitties.views.inflate

inline fun Context.recyclerView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RecyclerView.() -> Unit = {}
): RecyclerView = inflate(R.layout.recyclerview_with_scrollbars, id, theme, initView)

inline fun View.recyclerView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RecyclerView.() -> Unit = {}
) = context.recyclerView(id, theme, initView)

inline fun Ui.recyclerView(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: RecyclerView.() -> Unit = {}
) = ctx.recyclerView(id, theme, initView)

inline fun RecyclerView.LayoutManager.verticalListLayoutParams(
        block: RecyclerView.LayoutParams.() -> Unit = {}
): RecyclerView.LayoutParams = generateDefaultLayoutParams().apply {
    width = MATCH_PARENT
    height = WRAP_CONTENT
}.apply(block)

inline fun RecyclerView.LayoutManager.horizontalListLayoutParams(
        block: RecyclerView.LayoutParams.() -> Unit = {}
): RecyclerView.LayoutParams = generateDefaultLayoutParams().apply {
    width = WRAP_CONTENT
    height = MATCH_PARENT
}.apply(block)

private const val deprecationMsgExtensionFunction = "extension function available on " +
        "Context, View and Ui."

@Suppress("NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith")
@Deprecated("Replace v(::recyclerView) with recyclerView() $deprecationMsgExtensionFunction")
inline fun recyclerView(ctx: Context): RecyclerView = ctx.inflate(R.layout.recyclerview_with_scrollbars)
