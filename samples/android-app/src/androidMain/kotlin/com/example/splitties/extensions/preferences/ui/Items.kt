/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.preferences.ui

import android.graphics.drawable.Drawable
import splitties.preferences.BoolPref
import splitties.preferences.PrefDelegate

sealed class Item(val dependentPrefs: List<PrefDelegate<*>>)

sealed class BuiltInItem(dependentPrefs: List<PrefDelegate<*>> = emptyList()) : Item(dependentPrefs)
class OtherItem<T>(val content: T, dependentPrefs: List<PrefDelegate<*>>) : Item(dependentPrefs)

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
    open val delegate: PrefDelegate<D>,
    dependentPrefs: Array<out PrefDelegate<*>>
) : BuiltInItem(List(dependentPrefs.size + 1) { i ->
    if (i == dependentPrefs.size) delegate else dependentPrefs[i]
})

class BoolPrefItem(
    override val delegate: PrefDelegate<BoolPref>,
    val controlType: ControlType,
    val firstLineText: (value: Boolean) -> CharSequence,
    val secondLineText: (value: Boolean) -> CharSequence,
    val getIconDrawable: (value: Boolean) -> Drawable,
    val isEnabled: (value: Boolean) -> Boolean = { true },
    vararg dependentPrefs: PrefDelegate<*>
) : PrefItem<BoolPref>(delegate, dependentPrefs) {
    enum class ControlType { CheckBox, Switch }
}
