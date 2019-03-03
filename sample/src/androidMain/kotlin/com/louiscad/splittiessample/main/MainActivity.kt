/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample.main

import android.app.UiModeManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.about.AboutActivity
import com.louiscad.splittiessample.demo.DemoActivity
import com.louiscad.splittiessample.extensions.menu.addItem
import com.louiscad.splittiessample.extensions.menu.neverShowAsAction
import com.louiscad.splittiessample.extensions.toggleNightMode
import com.louiscad.splittiessample.prefs.GamePreferences
import com.louiscad.splittiessample.preview.permissions.PermissionsExampleActivity
import com.louiscad.splittiessample.preview.vibrator.VibrationEffect
import com.louiscad.splittiessample.preview.vibrator.vibrate
import com.louiscad.splittiessample.sayhello.SayHelloActivity
import kotlinx.coroutines.launch
import splitties.activities.start
import splitties.lifecycle.coroutines.lifecycleScope
import splitties.preferences.edit
import splitties.snackbar.longSnack
import splitties.systemservices.uiModeManager
import splitties.systemservices.vibrator
import splitties.toast.toast
import splitties.views.dsl.core.setContentView
import splitties.views.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = MainUi(this)
        setContentView(ui)
        uiModeManager.nightMode = UiModeManager.MODE_NIGHT_AUTO
        ui.launchDemoBtn.onClick { start<DemoActivity>() }
        ui.bePoliteWithPermissionsBtn.onClick { start<PermissionsExampleActivity>() }
        ui.sayHelloBtn.onClick { start<SayHelloActivity>() }
        ui.fab.onClick {
            lifecycleScope.launch {
                GamePreferences().edit {
                    currentLevel++
                    bossesFought++
                    lastTimePlayed = System.currentTimeMillis()
                } // TODO: 02/03/2017 Prove this working by moving it to a dedicated activity for prefs
                // TODO: 02/03/2017 With a UI showing prefs values.
                //TODO: Use PrefsAdapter in a bottom sheet for real example
            }
            vibrator.vibrate(vibe)
            ui.root.longSnack(R.string.cant_dislike_md)
        }
        ui.toggleNightModeBtn.onClick { toggleNightMode() }
    }

    override fun onCreateOptionsMenu(menu: Menu) = super.onCreateOptionsMenu(menu).also { _ ->
        menu.addItem(R.id.action_settings, R.string.action_settings, order = 100) {
            neverShowAsAction()
        }
        menu.addItem(R.id.action_about, R.string.about, order = 101) {
            neverShowAsAction()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> toast("No settings yet!").let { true }
        R.id.action_about -> start<AboutActivity>().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private val vibe = VibrationEffect.createWaveform(longArrayOf(0, 1, 50, 1))
    }
}
