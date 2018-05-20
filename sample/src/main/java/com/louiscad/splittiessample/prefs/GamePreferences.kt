/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package com.louiscad.splittiessample.prefs

import splitties.preferences.experimental.SuspendPrefsAccessor
import splitties.preferences.Preferences

class GamePreferences private constructor() : Preferences("gameState") {
    var magicNumber by intPref(0)
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")

    companion object : SuspendPrefsAccessor<GamePreferences>(::GamePreferences)
}
