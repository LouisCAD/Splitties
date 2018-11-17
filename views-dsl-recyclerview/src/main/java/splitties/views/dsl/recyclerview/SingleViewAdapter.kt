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

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.recyclerview.horizontalLayoutManager
import splitties.views.recyclerview.verticalLayoutManager

internal class SingleViewAdapter<V : View>(
    private val view: V,
    vertical: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val layoutManager = if (vertical) verticalLayoutManager() else horizontalLayoutManager()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = view.apply {
        @UseExperimental(ExperimentalSplittiesApi::class)
        layoutParams = layoutManager.verticalListLayoutParams()
    }.let { object : RecyclerView.ViewHolder(it) {} }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit
    override fun getItemCount() = 1
}
