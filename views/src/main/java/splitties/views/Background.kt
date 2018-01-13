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

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.support.annotation.ColorInt
import android.view.View
import kotlin.DeprecationLevel.HIDDEN

/**
 * This View's background [Drawable].
 *
 * This is an alias to the `background` property that is backwards compatible below API 16.
 */
inline var View.bg: Drawable?
    get() = background
    @Suppress("DEPRECATION")
    set(value) = if (SDK_INT < 16) setBackgroundDrawable(value) else background = value

/**
 * Set only property that takes a color int.
 *
 * This is an alias to [View.setBackgroundColor].
 */
inline var View.backgroundColor: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@ColorInt colorInt) = setBackgroundColor(colorInt)
