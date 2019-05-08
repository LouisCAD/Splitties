/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions

import android.app.UiModeManager
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import splitties.exceptions.illegalArg

fun AppCompatActivity.toggleNightMode() {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    val newNightMode = when (currentNightMode) {
        Configuration.UI_MODE_NIGHT_YES -> UiModeManager.MODE_NIGHT_NO
        Configuration.UI_MODE_NIGHT_UNDEFINED, Configuration.UI_MODE_NIGHT_NO -> UiModeManager.MODE_NIGHT_YES
        else -> illegalArg(currentNightMode)
    }
    delegate.setLocalNightMode(newNightMode)
    recreate()
}
