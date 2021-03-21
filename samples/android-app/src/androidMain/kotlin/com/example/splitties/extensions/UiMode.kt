/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package com.example.splitties.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import timber.log.Timber

inline fun AppCompatActivity.toggleNightMode() {
    delegate.toggleNightMode()
}

fun AppCompatDelegate.toggleNightMode() {
    val newNightMode = when (val currentNightMode = getDefaultNightMode()) {
        MODE_NIGHT_YES -> MODE_NIGHT_NO
        MODE_NIGHT_NO, MODE_NIGHT_FOLLOW_SYSTEM -> MODE_NIGHT_YES
        @Suppress("deprecation")
        MODE_NIGHT_AUTO -> MODE_NIGHT_YES
        else -> MODE_NIGHT_YES.also { Timber.w("Unexpected night mode: $currentNightMode") }
    }
    setDefaultNightMode(newNightMode)
    applyDayNight()
}
