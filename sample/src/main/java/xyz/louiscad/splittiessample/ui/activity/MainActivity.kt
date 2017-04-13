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
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import splitties.concurrency.uiLazy
import splitties.preferences.edit
import xyz.louiscad.splittiessample.R
import xyz.louiscad.splittiessample.extensions.toggleNightMode
import xyz.louiscad.splittiessample.extensions.uiModeManager
import xyz.louiscad.splittiessample.prefs.GamePreferences

class MainActivity : AppCompatActivity() {

    private val vibrator by uiLazy { getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiModeManager.nightMode = UiModeManager.MODE_NIGHT_AUTO
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        launchDemoButton.setOnClickListener {
            startActivity(Intent(this, DemoActivity::class.java))
        }
        fab.setOnClickListener {
            GamePreferences.edit {
                currentLevel++
                bossesFought++
                lastTimePlayed = System.currentTimeMillis()
            } // TODO: 02/03/2017 Prove this working by moving it to a dedicated activity for prefs
            // TODO: 02/03/2017 With a UI showing prefs values.
            vibrator.vibrate(pattern, -1)
            Snackbar.make(coordinator, R.string.cant_dislike_md, Snackbar.LENGTH_LONG).show()
        }
        concurrency_test_button.setOnClickListener {
            launch(CommonPool) { vibrator.vibrate(pattern, -1) }
        }
        toggle_night_mode_button.setOnClickListener { toggleNightMode() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private val pattern = longArrayOf(0, 1, 50, 1)
    }
}