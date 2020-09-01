/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl

import android.view.View
import splitties.views.dsl.core.BuildConfig
import splitties.views.dsl.core.Ui

val View.isInPreview: Boolean
    // On release builds, BuildConfig.DEBUG is a compile time constant, and this
    //  inlined extension property evaluates to the constant false, allowing the
    //  compiler to strip any code in impossible conditions (does not even need R8).
    inline get() = BuildConfig.DEBUG && isInEditMode

val Ui.isInPreview: Boolean
    // Inlined to false on release builds, like extension for View above.
    inline get() = BuildConfig.DEBUG && root.isInEditMode
