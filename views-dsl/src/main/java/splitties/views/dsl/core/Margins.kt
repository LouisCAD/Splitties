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

package splitties.views.dsl.core

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.support.annotation.Px
import android.view.ViewGroup
import kotlin.DeprecationLevel.HIDDEN

inline var ViewGroup.MarginLayoutParams.horizontalMargin: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@Px value) {
        leftMargin = value
        rightMargin = value
    }

inline var ViewGroup.MarginLayoutParams.verticalMargin: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@Px value) {
        topMargin = value
        bottomMargin = value
    }

inline var ViewGroup.MarginLayoutParams.margin: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@Px value) {
        leftMargin = value
        topMargin = value
        rightMargin = value
        bottomMargin = value
    }

inline var ViewGroup.MarginLayoutParams.startMargin: Int
    get() = if (SDK_INT >= JELLY_BEAN_MR1) marginStart else leftMargin
    set(@Px value) {
        if (SDK_INT >= JELLY_BEAN_MR1) marginStart = value
        else leftMargin = value
    }

inline var ViewGroup.MarginLayoutParams.endMargin: Int
    get() = if (SDK_INT >= JELLY_BEAN_MR1) marginEnd else rightMargin
    set(@Px value) {
        if (SDK_INT >= JELLY_BEAN_MR1) marginEnd = value
        else rightMargin = value
    }
