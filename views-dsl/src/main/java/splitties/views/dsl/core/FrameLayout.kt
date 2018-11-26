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

package splitties.views.dsl.core

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.FrameLayout

/**
 * Default gravity is treated by FrameLayout as: [Gravity.TOP] or [Gravity.START].
 */
inline fun FrameLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    @SuppressLint("InlinedApi")
    gravity: Int = FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY,
    initParams: FrameLayout.LayoutParams.() -> Unit = {}
): FrameLayout.LayoutParams = FrameLayout.LayoutParams(width, height).also {
    it.gravity = gravity
}.apply(initParams)
