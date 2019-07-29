/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import splitties.exceptions.illegalArg

fun Activity.toggleNightMode() {
    val newNightMode = when (val currentNightMode = getDefaultNightMode()) {
        MODE_NIGHT_YES -> MODE_NIGHT_NO
        MODE_NIGHT_NO, MODE_NIGHT_AUTO, MODE_NIGHT_FOLLOW_SYSTEM -> MODE_NIGHT_YES
        else -> illegalArg(currentNightMode)
    }
    setDefaultNightMode(newNightMode)
    recreate()
}
