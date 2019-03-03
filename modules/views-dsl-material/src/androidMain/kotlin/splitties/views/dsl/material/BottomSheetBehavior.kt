/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.material

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import splitties.views.dsl.core.Ui
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Creates a [BottomSheetBehavior] and applies the passed [initBehavior] lambda on it.
 */
@Suppress("unused") // Scopes visibility of the function to where it's meant to be used.
inline fun <V : View> Ui.bottomSheetBehavior(
    initBehavior: BottomSheetBehavior<V>.() -> Unit = {}
): BottomSheetBehavior<V> {
    contract { callsInPlace(initBehavior, InvocationKind.EXACTLY_ONCE) }
    return BottomSheetBehavior<V>().apply(initBehavior)
}

/**
 * Returns `false` if the associated bottom sheet was already expanded, returns `true` and expands
 * it otherwise.
 */
fun BottomSheetBehavior<*>.expand(): Boolean {
    return if (expanded) false else true.also { expanded = true }
}

/**
 * Returns `false` if the associated bottom sheet was already hidden, returns `true` and hides it
 * otherwise.
 * The bottom sheet must be hideable to hide the bottom sheet, or an [IllegalStateException] will be
 * thrown.
 */
fun BottomSheetBehavior<*>.hide(): Boolean {
    return if (hidden) false else true.also { hidden = true }
}

/**
 * True if the associated bottom sheet is expanded. Setting a `false` value hides the bottom sheet
 * if it is hideable, and collapses it otherwise.
 */
inline var BottomSheetBehavior<*>.expanded: Boolean
    get() = state == STATE_EXPANDED
    set(expand) {
        state = if (expand) STATE_EXPANDED else if (isHideable) STATE_HIDDEN else STATE_COLLAPSED
    }

/**
 * True if the associated bottom sheet is hidden. Setting a `false` value expands the bottom sheet.
 * The bottom sheet must be hideable to hide the bottom sheet, or an [IllegalStateException] will be
 * thrown.
 */
inline var BottomSheetBehavior<*>.hidden: Boolean
    get() = state == STATE_HIDDEN
    set(hide) {
        check(isHideable) { "Can't hide a non hideable bottom sheet!" }
        state = if (hide) STATE_HIDDEN else STATE_EXPANDED
    }
