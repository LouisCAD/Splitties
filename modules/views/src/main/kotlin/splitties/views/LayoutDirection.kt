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

import android.os.Build.VERSION.SDK_INT
import android.view.View

/**
 * True if layout direction is **left to right**, like in English.
 *
 * This is always true on API 16 and below since layout direction support only came in API 17.
 *
 * @see isRtl
 */
inline val View.isLtr get() = SDK_INT < 17 || layoutDirection == View.LAYOUT_DIRECTION_LTR

/**
 * True if layout direction is **right to left**, like in Arabic.
 *
 * This is always false on API 16 and below since layout direction support only came in API 17.
 *
 * @see isLtr
 */
inline val View.isRtl get() = !isLtr
