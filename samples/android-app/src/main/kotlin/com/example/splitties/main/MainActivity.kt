/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.splitties.R
import com.example.splitties.about.AboutActivity
import com.example.splitties.demo.DemoActivity
import com.example.splitties.extensions.coroutines.launchLoop
import com.example.splitties.extensions.menu.addItem
import com.example.splitties.extensions.menu.neverShowAsAction
import com.example.splitties.extensions.toggleNightMode
import com.example.splitties.permissions.PermissionsExampleActivity
import com.example.splitties.preferences.SamplePreferences
import com.example.splitties.preferences.ui.PreferencesBottomSheetDialogFragment
import com.example.splitties.prefs.GamePreferences
import com.example.splitties.preview.sound.playDiapason
import com.example.splitties.preview.vibrator.VibrationEffect
import com.example.splitties.preview.vibrator.vibrate
import com.example.splitties.sayhello.SayHelloActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import splitties.activities.start
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.coroutines.showAndAwaitOkOrDismiss
import splitties.coroutines.repeatWhileActive
import splitties.fragments.showAsync
import splitties.preferences.edit
import splitties.snackbar.longSnack
import splitties.systemservices.vibrator
import splitties.toast.UnreliableToastApi
import splitties.toast.toast
import splitties.views.dsl.core.*
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED as Undispatched

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui: MainAndroidUi = MainUiImpl(this)
        lifecycleScope.launch(start = Undispatched) { run(ui) }
        setContentView(ui)
    }

    private suspend fun run(ui: MainAndroidUi): Unit = coroutineScope {
        launch(start = Undispatched) {
            val vibe = VibrationEffect.createWaveform(longArrayOf(0, 1, 50, 1))
            repeatWhileActive {
                ui.awaitFabClick()
                vibrator.vibrate(vibe)
                ui.root.longSnack(R.string.cant_dislike_md)
                GamePreferences().edit {
                    currentLevel++
                    bossesFought++
                    lastTimePlayed = System.currentTimeMillis()
                }
            }
        }
        launchLoop(start = Undispatched) {
            ui.awaitTrySoundRequest()
            val job = launch {
                playDiapason(durationInMillis = 10_000)
            }
            ui.awaitTrySoundRequest()
            job.cancelAndJoin()
        }
        launchLoop(start = Undispatched) {
            ui.awaitLaunchMaterialListDemoRequest()
            start<DemoActivity>()
            delay(700)
        }
        launchLoop(start = Undispatched) {
            ui.awaitLaunchPermissionDemoRequest()
            start<PermissionsExampleActivity>()
            delay(700)
        }
        launchLoop(start = Undispatched) {
            ui.awaitLaunchSayHelloDemoRequest()
            start<SayHelloActivity>()
            delay(700)
        }
        launchLoop(start = Undispatched) {
            ui.awaitToggleNightModeRequest()
            toggleNightMode()
        }
        launch(start = Undispatched) {
            val prefs = SamplePreferences()
            prefs.enableAnnoyingFeaturesField.valueFlow().collectLatest { annoy ->
                if (annoy) {
                    if (prefs.showAnnoyingPopupAtLaunch) {
                        alertDialog(
                            title = "Launching the annoyance",
                            message = "Annoying features are enabled and the " +
                                    "\"Popup at launch\" option is checked."
                        ).showAndAwaitOkOrDismiss()
                    }
                    prefs.showAnnoyingPopupInLoopField.valueFlow().collectLatest { loop ->
                        if (loop) while (isActive) {
                            @OptIn(UnreliableToastApi::class)
                            toast("Next up in 6 seconds")
                            delay(6000)
                            alertDialog(
                                title = "Looping the annoyance",
                                message = "Annoying features are enabled and the " +
                                        "\"Show annoying popup in loop\" option is checked."
                            ).showAndAwaitOkOrDismiss()
                        }
                    }
                }
            }
        }
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
        R.id.action_settings -> showAsync(::PreferencesBottomSheetDialogFragment).let { true }
        R.id.action_about -> start<AboutActivity>().let { true }
        else -> super.onOptionsItemSelected(item)
    }
}
