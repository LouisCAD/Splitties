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
package com.louiscad.splittiessample.extensions.drawables

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.AttrRes
import android.util.StateSet
import splitties.exceptions.unsupported

/**
 * Usage example:
 * ```kotlin
 * val drawable = drawableStateList {
 *     addForState(::GradientDrawable, android.R.attr.state_pressed) {
 *         shape = RECTANGLE
 *         cornerRadius = dp(4)
 *         solidColor = Color.LTGRAY
 *     }
 *     addForRemainingStates(::GradientDrawable) {
 *         shape = RECTANGLE
 *         cornerRadius = dp(4)
 *         solidColor = Color.WHITE
 *     }
 * }
 * ```
 * The code above is equivalent to the following xml, minus binary xml parsing overhead:
 * ```xml
 * <selector xmlns:android="http://schemas.android.com/apk/res/android">
 *     <item android:state_pressed="true">
 *         <shape android:shape="rectangle">
 *             <solid android:color="#efefef"/>
 *             <corners android:radius="4dp"/>
 *         </shape>
 *     </item>
 *     <item>
 *         <shape android:shape="rectangle">
 *             <solid android:color="#fff"/>
 *             <corners android:radius="4dp"/>
 *         </shape>
 *     </item>
 * </selector>
 * ```
 * You can see the Kotlin snippet is 2 lines shorter, but the real question is:
 * ## Which one is more readable?
 */
inline fun drawableStateList(
    addStates: StateListDrawable.() -> Unit
): Drawable = StateListDrawable().apply(addStates)

/**
 * For [state] parameter: see the attr resources starting with **`android.R.attr.state_`**.
 *
 * Use minus (`-`) for [state] = false.
 */
inline fun <D : Drawable> StateListDrawable.addForState(
    newDrawableRef: () -> D,
    @AttrRes state: Int,
    drawableConfig: D.() -> Unit
) = addState(intArrayOf(state), newDrawableRef().apply(drawableConfig))

/**
 * For [stateSet] parameter: see the attr resources starting with **`android.R.attr.state_`**.
 *
 * Use minus (`-`) for state = false in [stateSet] values.
 */
inline fun <D : Drawable> StateListDrawable.addForStates(
    newDrawableRef: () -> D,
    @AttrRes vararg stateSet: Int,
    drawableConfig: D.() -> Unit
) = addState(stateSet, newDrawableRef().apply(drawableConfig))

inline fun <D : Drawable> StateListDrawable.addForRemainingStates(
    newDrawableRef: () -> D,
    drawableConfig: D.() -> Unit
) = addState(StateSet.WILD_CARD, newDrawableRef().apply(drawableConfig))

@Deprecated(
    "Using addForStates with empty state set is misleading",
    ReplaceWith("addForRemainingStates(newDrawableRef, drawableConfig)"),
    level = DeprecationLevel.ERROR
) // FOOL GUARD, DO NOT REMOVE
@Suppress("unused")
inline fun <D : Drawable> StateListDrawable.addForStates(
    newDrawableRef: () -> D,
    drawableConfig: D.() -> Unit
): Nothing = unsupported()
