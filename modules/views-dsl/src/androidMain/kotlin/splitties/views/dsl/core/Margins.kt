/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.core

import android.os.Build.VERSION.SDK_INT
import android.view.ViewGroup
import androidx.annotation.Px
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
    get() = if (SDK_INT >= 17) marginStart else leftMargin
    set(@Px value) {
        if (SDK_INT >= 17) marginStart = value
        else leftMargin = value
    }

inline var ViewGroup.MarginLayoutParams.endMargin: Int
    get() = if (SDK_INT >= 17) marginEnd else rightMargin
    set(@Px value) {
        if (SDK_INT >= 17) marginEnd = value
        else rightMargin = value
    }
