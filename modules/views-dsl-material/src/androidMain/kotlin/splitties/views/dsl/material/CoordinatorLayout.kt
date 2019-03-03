/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.material

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import splitties.views.dsl.coordinatorlayout.defaultLParams
import splitties.views.dsl.core.matchParent
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams as LP

inline fun CoordinatorLayout.contentScrollingWithAppBarLParams(
    initParams: LP.() -> Unit = {}
): LP {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return defaultLParams(matchParent, matchParent) {
        behavior = AppBarLayout.ScrollingViewBehavior()
        initParams()
    }
}
