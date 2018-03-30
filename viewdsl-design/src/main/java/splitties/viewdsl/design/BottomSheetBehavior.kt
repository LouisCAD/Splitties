/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package splitties.viewdsl.design

import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED
import android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED
import android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN
import android.view.View
import splitties.viewdsl.core.Ui

/**
 * Creates a [BottomSheetBehavior] and applies the passed [initBehavior] lambda on it.
 */
@Suppress("unused") // Scopes visibility of the function to where it's meant to be used.
inline fun <V : View> Ui.bottomSheetBehavior(
        initBehavior: BottomSheetBehavior<V>.() -> Unit = {}
) = BottomSheetBehavior<V>().apply(initBehavior)

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
