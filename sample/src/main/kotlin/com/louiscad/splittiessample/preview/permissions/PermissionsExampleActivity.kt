/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
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
package com.louiscad.splittiessample.preview.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.extensions.coroutines.DialogButton
import splitties.lifecycle.coroutines.awaitState
import splitties.lifecycle.coroutines.coroutineScope
import com.louiscad.splittiessample.extensions.coroutines.showAndAwait
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.yield
import splitties.activities.startActivity
import splitties.alertdialog.appcompat.alert
import splitties.alertdialog.appcompat.message
import splitties.dimensions.dip
import splitties.views.centerText
import splitties.views.dsl.core.*
import splitties.views.gravityCenter
import splitties.views.textAppearance

class PermissionsExampleActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n") // This is an example where i18n matters less than readable code.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch {
            ensureCalendarPermissionOrFinishActivity()
            contentView = frameLayout {
                add(textView {
                    textAppearance = R.style.TextAppearance_AppCompat_Headline
                    text = "Thanks for granting the permission!\n" +
                            "Nothing to see there now… :)"
                    centerText()
                }, lParams(gravity = gravityCenter) { margin = dip(16) })
            }
        }
    }

    private suspend fun ensureCalendarPermissionOrFinishActivity() {
        val calendarPermission = Manifest.permission.WRITE_CALENDAR
        if (!hasPermission(calendarPermission)) {
            val quit = alert {
                message = "We will ask for calendar permission.\n" +
                        "Don't grant it too soon if you want to test all cases from this sample!"
            }.quitOrOk()
            if (quit) {
                finish(); suspendCancellableCoroutine<Unit> { it.cancel() }
            }
        }
        while (!hasPermission(calendarPermission)) {
            try {
                requestPermission(calendarPermission)
                break
            } catch (e: PermissionDeniedException) {
                e.recoverOrFinishActivity()
            }
        }
    }

    private suspend fun PermissionDeniedException.recoverOrFinishActivity() {
        val quit: Boolean = if (doNotAskAgain) {
            alert {
                message = "You denied the permission permanently.\n" +
                        "You can grant it in the settings."
            }.quitOrOk()
        } else alert { message = "Please accept…" }.quitOrOk()
        if (quit) {
            finish(); suspendCancellableCoroutine<Unit> { it.cancel() }
        } else if (doNotAskAgain) {
            startActivity(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) {
                data = "package:$packageName".toUri()
            }
            yield() // Allow the activity start to take effect and pause this activity
            lifecycle.awaitState(Lifecycle.State.RESUMED) // Await user coming back
        }
    }

    private suspend fun AlertDialog.quitOrOk(): Boolean = showAndAwait(
        positiveButton = DialogButton.ok(false),
        negativeButton = DialogButton("Quit", true),
        dismissValue = false
    )
}
