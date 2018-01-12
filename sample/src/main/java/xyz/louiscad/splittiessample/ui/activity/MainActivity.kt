/*
 * Copyright (c) 2016. Louis Cognault Ayeva Derman
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

@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package xyz.louiscad.splittiessample.ui.activity

import android.app.UiModeManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import splitties.preferences.edit
import splitties.snackbar.longSnack
import splitties.systemservices.uiModeManager
import splitties.systemservices.vibrator
import splitties.views.onClick
import xyz.louiscad.splittiessample.R
import xyz.louiscad.splittiessample.extensions.toggleNightMode
import xyz.louiscad.splittiessample.prefs.GamePreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiModeManager.nightMode = UiModeManager.MODE_NIGHT_AUTO
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        launchDemoButton.onClick {
            startActivity(Intent(this, DemoActivity::class.java))
        }
        fab.onClick {
            GamePreferences.edit {
                currentLevel++
                bossesFought++
                lastTimePlayed = System.currentTimeMillis()
            } // TODO: 02/03/2017 Prove this working by moving it to a dedicated activity for prefs
            // TODO: 02/03/2017 With a UI showing prefs values.
            vibrator.vibrate(pattern, -1)
            coordinator.longSnack(R.string.cant_dislike_md)
        }
        toggle_night_mode_button.onClick { toggleNightMode() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private val pattern = longArrayOf(0, 1, 50, 1)
    }
}
