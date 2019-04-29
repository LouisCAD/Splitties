/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.extensions.drawables

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.annotation.AttrRes
import splitties.exceptions.unsupported

/**
 * Usage example:
 * ```kotlin
 * val drawable = drawableStateList {
 *     addForState(rectangleDrawable(), android.R.attr.state_pressed) {
 *         cornerRadius = dp(4)
 *         solidColor = Color.LTGRAY
 *     }
 *     addForRemainingStates(rectangleDrawable()) {
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
 * You can see the Kotlin snippet is 4 lines shorter (out of 14), but the real question is:
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
    drawable: D,
    @AttrRes state: Int,
    drawableConfig: D.() -> Unit = {}
) = addState(intArrayOf(state), drawable.apply(drawableConfig))

/**
 * For [stateSet] parameter: see the attr resources starting with **`android.R.attr.state_`**.
 *
 * Use minus (`-`) for state = false in [stateSet] values.
 */
inline fun <D : Drawable> StateListDrawable.addForStates(
    drawable: D,
    @AttrRes vararg stateSet: Int,
    drawableConfig: D.() -> Unit = {}
) = addState(stateSet, drawable.apply(drawableConfig))

inline fun <D : Drawable> StateListDrawable.addForRemainingStates(
    drawable: D,
    drawableConfig: D.() -> Unit = {}
) = addState(StateSet.WILD_CARD, drawable.apply(drawableConfig))

@Suppress("unused")
@Deprecated(
    message = "Using addForStates with empty state set is misleading",
    replaceWith = ReplaceWith("addForRemainingStates(drawable, drawableConfig)"),
    level = DeprecationLevel.ERROR
)
inline fun <D : Drawable> StateListDrawable.addForStates(
    drawable: D,
    drawableConfig: D.() -> Unit = {}
): Nothing = unsupported()

typealias NewDrawableRef<D> = () -> D

/**
 * For [state] parameter: see the attr resources starting with **`android.R.attr.state_`**.
 *
 * Use minus (`-`) for [state] = false.
 */
inline fun <D : Drawable> StateListDrawable.addForState(
    createDrawable: NewDrawableRef<D>,
    @AttrRes state: Int,
    drawableConfig: D.() -> Unit
) = addState(intArrayOf(state), createDrawable().apply(drawableConfig))

/**
 * For [stateSet] parameter: see the attr resources starting with **`android.R.attr.state_`**.
 *
 * Use minus (`-`) for state = false in [stateSet] values.
 */
inline fun <D : Drawable> StateListDrawable.addForStates(
    createDrawable: NewDrawableRef<D>,
    @AttrRes vararg stateSet: Int,
    drawableConfig: D.() -> Unit
) = addState(stateSet, createDrawable().apply(drawableConfig))

inline fun <D : Drawable> StateListDrawable.addForRemainingStates(
    createDrawable: NewDrawableRef<D>,
    drawableConfig: D.() -> Unit
) = addState(StateSet.WILD_CARD, createDrawable().apply(drawableConfig))

@Suppress("unused")
@Deprecated(
    message = "Using addForStates with empty state set is misleading",
    replaceWith = ReplaceWith("addForRemainingStates(createDrawable, drawableConfig)"),
    level = DeprecationLevel.ERROR
)
inline fun <D : Drawable> StateListDrawable.addForStates(
    createDrawable: NewDrawableRef<D>,
    drawableConfig: D.() -> Unit
): Nothing = unsupported()
