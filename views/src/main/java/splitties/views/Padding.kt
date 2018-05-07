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

import android.support.annotation.Px
import android.view.View
import splitties.dimensions.dip
import kotlin.DeprecationLevel.HIDDEN

inline var View.padding: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@Px value) = setPadding(value, value, value, value)

inline var View.horizontalPadding: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@Px value) = setPadding(value, paddingTop, value, paddingBottom)

inline var View.verticalPadding: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@Px value) = setPadding(paddingLeft, value, paddingRight, value)

inline var View.topPadding: Int
    get() = paddingTop
    set(@Px value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

inline var View.bottomPadding: Int
    get() = paddingBottom
    set(@Px value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

fun View.setPaddingDp(start: Int = 0,
                      top: Int = 0,
                      end: Int = 0,
                      bottom: Int = 0) {
    val left = if (isLtr) start else end
    val right = if (isLtr) end else start
    setPadding(dip(left), dip(top), dip(right), dip(bottom))
}
