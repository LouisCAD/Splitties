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
@file:Suppress("unused")

package splitties.views

import android.view.Gravity
import android.view.View

inline val View.gravityCenter: Int get() = Gravity.CENTER
inline val View.gravityCenterVertical: Int get() = Gravity.CENTER_VERTICAL
inline val View.gravityCenterHorizontal: Int get() = Gravity.CENTER_HORIZONTAL

inline val View.gravityVerticalCenter: Int get() = gravityCenterVertical
inline val View.gravityHorizontalCenter: Int get() = gravityCenterHorizontal

inline val View.gravityStart: Int get() = Gravity.START
inline val View.gravityTop: Int get() = Gravity.TOP
inline val View.gravityEnd: Int get() = Gravity.END
inline val View.gravityBottom: Int get() = Gravity.BOTTOM

inline val View.gravityStartBottom: Int get() = Gravity.START or Gravity.BOTTOM
inline val View.gravityStartTop: Int get() = Gravity.START or Gravity.TOP
inline val View.gravityEndBottom: Int get() = Gravity.END or Gravity.BOTTOM
inline val View.gravityEndTop: Int get() = Gravity.END or Gravity.TOP

inline val View.gravityBottomStart: Int get() = gravityStartBottom
inline val View.gravityTopStart: Int get() = gravityStartTop
inline val View.gravityBottomEnd: Int get() = gravityEndBottom
inline val View.gravityTopEnd: Int get() = gravityEndTop

inline val View.gravityStartCenter: Int get() = Gravity.START or Gravity.CENTER_VERTICAL
inline val View.gravityEndCenter: Int get() = Gravity.END or Gravity.CENTER_VERTICAL

inline val View.gravityTopCenter: Int get() = Gravity.TOP or Gravity.CENTER_HORIZONTAL
inline val View.gravityBottomCenter: Int get() = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
