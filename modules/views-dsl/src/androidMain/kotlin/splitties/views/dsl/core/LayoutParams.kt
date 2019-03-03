/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.core

import android.view.View
import android.view.ViewGroup

/**
 * **A LESS CAPITALIZED ALIAS** to [ViewGroup.LayoutParams.MATCH_PARENT] that is only
 * visible inside [ViewGroup]s.
 */
@Suppress("unused")
inline val View.matchParent
    get() = ViewGroup.LayoutParams.MATCH_PARENT

/**
 * **A LESS CAPITALIZED ALIAS** to [ViewGroup.LayoutParams.WRAP_CONTENT] that is only
 * visible inside [ViewGroup]s.
 */
@Suppress("unused")
inline val View.wrapContent
    get() = ViewGroup.LayoutParams.WRAP_CONTENT
