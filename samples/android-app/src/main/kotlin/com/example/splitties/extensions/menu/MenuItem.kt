/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("NOTHING_TO_INLINE")

package com.example.splitties.extensions.menu

import android.view.MenuItem
import splitties.bitflags.withFlag

inline fun MenuItem.neverShowAsAction() = setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
inline fun MenuItem.alwaysShowAsAction(withText: Boolean = false) {
    val flags = MenuItem.SHOW_AS_ACTION_ALWAYS
    setShowAsAction(if (withText) flags.withFlag(MenuItem.SHOW_AS_ACTION_WITH_TEXT) else flags)
}

inline fun MenuItem.showAsActionIfRoom(withText: Boolean = false) {
    val flags = MenuItem.SHOW_AS_ACTION_IF_ROOM
    setShowAsAction(if (withText) flags.withFlag(MenuItem.SHOW_AS_ACTION_WITH_TEXT) else flags)
}
