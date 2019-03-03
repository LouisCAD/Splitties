/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.constraintlayout

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import splitties.collections.forEachWithIndex
import splitties.views.dsl.core.add
import splitties.views.dsl.core.endMargin
import splitties.views.dsl.core.startMargin
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams as LP

@Suppress("unused")
inline val ConstraintLayout.packed
    get() = LP.CHAIN_PACKED
@Suppress("unused")
inline val ConstraintLayout.spread
    get() = LP.CHAIN_SPREAD
@Suppress("unused")
inline val ConstraintLayout.spreadInside
    get() = LP.CHAIN_SPREAD_INSIDE

inline fun ConstraintLayout.horizontalChain(
    views: List<View>,
    style: Int = spread,
    defaultWidth: Int = matchConstraints,
    defaultHeight: Int = matchConstraints,
    initFirstViewParams: ConstraintLayout.LayoutParams.() -> Unit = { startOfParent() },
    initLastViewParams: ConstraintLayout.LayoutParams.() -> Unit = { endOfParent() },
    alignVerticallyOnFirstView: Boolean = false,
    initParams: ConstraintLayout.LayoutParams.(View) -> Unit = {}
) {
    lateinit var previousView: View
    val lastIndex = views.lastIndex
    views.forEachWithIndex { index, view ->
        val existingLParams = view.layoutParams as? ConstraintLayout.LayoutParams
        val layoutParams = existingLParams?.apply {
            width = defaultWidth
            height = defaultHeight
        } ?: lParams(defaultWidth, defaultHeight)
        layoutParams.apply {
            initParams(view)
            if (index == 0) {
                initFirstViewParams()
                horizontalChainStyle = style
            } else {
                startToEndOf(previousView)
                if (alignVerticallyOnFirstView) alignVerticallyOn(previousView)
            }
            if (index == lastIndex) initLastViewParams()
            else {
                val nextView = views[index + 1]
                endToStartOf(nextView)
            }
        }
        if (existingLParams == null) add(view, layoutParams)
        else view.layoutParams = existingLParams // Refresh to take changes into account
        previousView = view
    }
}


inline fun ConstraintLayout.verticalChain(
    views: List<View>,
    style: Int = spread,
    defaultWidth: Int = matchConstraints,
    defaultHeight: Int = matchConstraints,
    initFirstViewParams: ConstraintLayout.LayoutParams.() -> Unit = { topOfParent() },
    initLastViewParams: ConstraintLayout.LayoutParams.() -> Unit = { bottomOfParent() },
    alignHorizontallyOnFirstView: Boolean = false,
    initParams: ConstraintLayout.LayoutParams.(View) -> Unit = {}
) {
    lateinit var previousView: View
    val lastIndex = views.lastIndex
    views.forEachWithIndex { index, view ->
        val existingLParams = view.layoutParams as? ConstraintLayout.LayoutParams
        val layoutParams = existingLParams?.apply {
            width = defaultWidth
            height = defaultHeight
        } ?: lParams(defaultWidth, defaultHeight)
        layoutParams.apply {
            initParams(view)
            if (index == 0) {
                initFirstViewParams()
                verticalChainStyle = style
            } else {
                topToBottomOf(previousView)
                if (alignHorizontallyOnFirstView) alignHorizontallyOn(previousView)
            }
            if (index == lastIndex) initLastViewParams()
            else {
                val nextView = views[index + 1]
                bottomToTopOf(nextView)
            }
        }
        if (existingLParams == null) add(view, layoutParams)
        else view.layoutParams = layoutParams // Refresh to take changes into account
        previousView = view
    }
}

var List<View>.horizontalMargin: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@Px value) {
        first().updateLayoutParams<ViewGroup.MarginLayoutParams> {
            startMargin = value
        }
        last().updateLayoutParams<ViewGroup.MarginLayoutParams> {
            endMargin = value
        }
    }

var List<View>.verticalMargin: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@Px value) {
        first().updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = value
        }
        last().updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = value
        }
    }

