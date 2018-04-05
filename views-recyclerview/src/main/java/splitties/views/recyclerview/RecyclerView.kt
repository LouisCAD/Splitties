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

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * `false` by default.
 * If you can guarantee that all the items that this [RecyclerView] will display
 * (as defined from its adapter), have all the same size (e.g. homogeneous height for a list
 * displayed with a vertical [LinearLayoutManager]), set this property to `true` so the
 * [RecyclerView] can enable some optimizations that will improve efficiency.
 *
 * @see RecyclerView.setHasFixedSize
 */
inline var RecyclerView.fixedSize: Boolean
    get() = hasFixedSize()
    set(value) = setHasFixedSize(value)
