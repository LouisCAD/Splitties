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

package splitties.views

import android.content.Context
import android.support.annotation.CheckResult
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import splitties.systemservices.layoutInflater

/**
 * @see LayoutInflater.inflate
 */
@CheckResult inline fun <V : View> LayoutInflater.inflate(@LayoutRes layoutResId: Int): V {
    @Suppress("UNCHECKED_CAST") return inflate(layoutResId, null, false) as V
}

/**
 * @see LayoutInflater.inflate
 */
@CheckResult inline fun <V : View> Context.inflate(@LayoutRes layoutResId: Int): V {
    return layoutInflater.inflate(layoutResId)
}

/**
 * @param attachToRoot must be explicitly false if used for a `RecyclerView` item.
 *
 * @see inflateAndAttach
 * @see LayoutInflater.inflate
 */
inline fun <V : View> ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean): V {
    @Suppress("UNCHECKED_CAST") return layoutInflater.inflate(layoutRes, this, attachToRoot) as V
}

/**
 * @see LayoutInflater.inflate
 */
inline fun ViewGroup.inflateAndAttach(@LayoutRes layoutRes: Int) {
    layoutInflater.inflate(layoutRes, this, true)
}
