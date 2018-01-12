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

package splitties.views

import android.view.View

/**
 * True if this View's visibility is [View.VISIBLE].
 *
 * If you set it to false, then this View's visibility will become [View.INVISIBLE].
 * See [showIf] and [gone] if you want to have [View.GONE] instead.
 */
inline var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

/**
 * True if this View's visibility is [View.GONE].
 *
 * If you set it to false, then this View's visibility will become [View.VISIBLE].
 *
 * @see showIf
 * @see visible
 */
inline var View.gone: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }

/**
 * Sets this View's visibility to [View.VISIBLE] if [condition] is true, [View.GONE] otherwise.
 *
 * @see visible
 * @see gone
 */
@Suppress("NOTHING_TO_INLINE")
inline fun View.showIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}
