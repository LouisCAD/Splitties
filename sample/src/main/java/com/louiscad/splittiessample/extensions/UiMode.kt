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

package com.louiscad.splittiessample.extensions

import android.app.UiModeManager
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
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
