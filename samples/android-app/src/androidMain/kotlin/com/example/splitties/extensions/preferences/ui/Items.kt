/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.preferences.ui

import android.graphics.drawable.Drawable
import splitties.preferences.BoolPref
import splitties.preferences.PrefDelegate

sealed class Item(val dependentPrefs: List<PrefDelegate<*>>)

sealed class BuiltInItem(dependentPrefs: List<PrefDelegate<*>> = emptyList()) : Item(dependentPrefs)
class OtherItem<T>(
    val content: T,
    dependentPrefs: List<PrefDelegate<*>> = emptyList()
) : Item(dependentPrefs)

class StaticHeader(val headerText: CharSequence) : BuiltInItem()

class IconTwoLinesItem(
    val firstLineText: () -> CharSequence,
    val secondLineText: () -> CharSequence,
    val getIconDrawable: () -> Drawable,
    val isEnabled: () -> Boolean = { true },
    vararg dependentPrefs: PrefDelegate<*>,
    val onClick: () -> Unit
) : BuiltInItem(dependentPrefs.asList())

sealed class PrefItem<D : PrefDelegate<*>>(
    @Suppress("CanBeParameter") // Enforce PrefItem to have a delegate.
    open val delegate: D,
    dependentPrefs: List<PrefDelegate<*>>
) : BuiltInItem(dependentPrefs + delegate)

class BoolPrefItem<P : BoolPrefItem.Presentation>(
    override val delegate: BoolPref,
    val presentation: P,
    val firstLineText: (value: Boolean) -> CharSequence,
    val secondLineText: (value: Boolean) -> CharSequence,
    val getIconDrawable: (value: Boolean) -> Drawable,
    val isEnabled: (value: Boolean) -> Boolean = { true },
    dependentPrefs: List<PrefDelegate<*>> = emptyList()
) : PrefItem<BoolPref>(delegate, dependentPrefs) {

    sealed class Presentation {
        sealed class CheckBox : Presentation() {
            object TwoLines : CheckBox()
            object TwoText : CheckBox()
        }

        sealed class Switch : Presentation() {
            object TwoLines : Switch()
            object TwoText : Switch()
        }
    }
}

typealias BoolPrefCheckBoxItem = BoolPrefItem<BoolPrefItem.Presentation.CheckBox>
typealias BoolPrefSwitchItem = BoolPrefItem<BoolPrefItem.Presentation.Switch>
