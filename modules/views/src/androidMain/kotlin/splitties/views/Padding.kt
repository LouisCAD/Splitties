/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views

import android.os.Build.VERSION.SDK_INT
import android.view.View
import androidx.annotation.Px
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

inline var View.startPadding: Int
    get() = if (SDK_INT >= 17) paddingStart else paddingLeft
    set(@Px value) = when {
        SDK_INT >= 17 -> setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)
        else -> setPadding(value, paddingTop, paddingRight, paddingBottom)
    }

inline var View.endPadding: Int
    get() = if (SDK_INT >= 17) paddingEnd else paddingRight
    set(@Px value) = when {
        SDK_INT >= 17 -> setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)
        else -> setPadding(paddingLeft, paddingTop, value, paddingBottom)
    }

inline var View.leftPadding: Int
    get() = paddingLeft
    set(@Px value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

inline var View.rightPadding: Int
    get() = paddingRight
    set(@Px value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

fun View.setPaddingDp(
    start: Int = 0,
    top: Int = 0,
    end: Int = 0,
    bottom: Int = 0
) {
    val left = if (isLtr) start else end
    val right = if (isLtr) end else start
    setPadding(dip(left), dip(top), dip(right), dip(bottom))
}
