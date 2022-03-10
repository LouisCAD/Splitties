/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.prefs

import splitties.coroutines.SuspendLazy
import splitties.coroutines.suspendBlockingLazyIO
import splitties.preferences.Preferences

class GamePreferences private constructor() : Preferences("gameState") {
    companion object : SuspendLazy<GamePreferences> by suspendBlockingLazyIO(::GamePreferences)

    var magicNumber by intPref("magicNumber", 0)
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")
}
