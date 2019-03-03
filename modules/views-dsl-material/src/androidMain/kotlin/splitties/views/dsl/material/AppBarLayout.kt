/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.material

import com.google.android.material.appbar.AppBarLayout
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.wrapContent
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import com.google.android.material.appbar.AppBarLayout.LayoutParams as LP

@Suppress("unused")
val AppBarLayout.SCROLL
    get() = LP.SCROLL_FLAG_SCROLL
@Suppress("unused")
val AppBarLayout.EXIT_UNTIL_COLLAPSED
    get() = LP.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
@Suppress("unused")
val AppBarLayout.ENTER_ALWAYS
    get() = LP.SCROLL_FLAG_ENTER_ALWAYS
@Suppress("unused")
val AppBarLayout.ENTER_ALWAYS_COLLAPSED: Int
    get() = LP.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
@Suppress("unused")
val AppBarLayout.SNAP
    get() = LP.SCROLL_FLAG_SNAP

inline fun AppBarLayout.defaultLParams(
    width: Int = matchParent,
    height: Int = wrapContent,
    scrollFlags: Int = ENTER_ALWAYS,
    initParams: LP.() -> Unit = {}
): LP {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return LP(width, height).also { it.scrollFlags = scrollFlags }.apply(initParams)
}
