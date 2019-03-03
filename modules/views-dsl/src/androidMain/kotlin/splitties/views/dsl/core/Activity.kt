/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.core

import android.app.Activity
import android.view.View
import kotlin.DeprecationLevel.HIDDEN

inline var Activity.contentView: View
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setContentView(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Activity.setContentView(ui: Ui) = setContentView(ui.root)
