/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
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
