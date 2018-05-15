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

package splitties.views.appcompat

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.support.v7.widget.TooltipCompat
import android.view.View
import kotlin.DeprecationLevel.HIDDEN

inline fun View.contentDescAsTooltip() {
    tooltipTxt = contentDescription
}

var View.tooltipTxt: CharSequence?
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = if (SDK_INT >= O) tooltipText = value else {
        TooltipCompat.setTooltipText(this, value)
    }
