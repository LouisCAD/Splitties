/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.material

import androidx.annotation.ColorInt
import com.google.android.material.appbar.CollapsingToolbarLayout

inline var CollapsingToolbarLayout.contentScrimColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@ColorInt value) = setContentScrimColor(value)
