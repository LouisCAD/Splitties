/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.louiscad.splittiessample.extensions.drawables

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.annotation.AttrRes
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
