/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.extensions.menu

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@UseExperimental(ExperimentalContracts::class)
inline fun Menu.addItem(
    @IdRes id: Int,
    @StringRes titleRes: Int,
    groupId: Int = Menu.NONE,
    order: Int = Menu.NONE,
    block: MenuItem.() -> Unit = {}
): MenuItem {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return add(groupId, id, order, titleRes)!!.apply(block)
}
