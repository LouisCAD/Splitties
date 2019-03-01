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
package splitties.views.dsl.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun View.wrapInRecyclerView(
    horizontal: Boolean = false,
    @IdRes id: Int = View.NO_ID,
    initView: RecyclerView.() -> Unit = {}
): RecyclerView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return wrapInRecyclerView(horizontal, id).apply(initView)
}

fun View.wrapInRecyclerView(
    horizontal: Boolean = false,
    @IdRes id: Int = View.NO_ID
): RecyclerView = recyclerView(id) {
    val contentAdapter = SingleViewAdapter(this@wrapInRecyclerView, vertical = !horizontal)
    layoutManager = contentAdapter.layoutManager
    adapter = contentAdapter
}
