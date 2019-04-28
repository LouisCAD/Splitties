/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.prefs

import com.example.splitties.extensions.coroutines.SuspendLazy
import kotlinx.coroutines.Dispatchers
import splitties.preferences.Preferences

class GamePreferences private constructor() : Preferences("gameState") {
    companion object : SuspendLazy<GamePreferences>(Dispatchers.IO, ::GamePreferences)

    var magicNumber by intPref(0)
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")
}
