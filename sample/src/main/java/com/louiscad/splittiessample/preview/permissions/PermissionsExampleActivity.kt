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
import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import androidx.core.net.toUri
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.extensions.coroutines.DialogButton
import com.louiscad.splittiessample.extensions.coroutines.awaitState
import com.louiscad.splittiessample.extensions.coroutines.coroutineScope
import com.louiscad.splittiessample.extensions.coroutines.showAndAwait
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import splitties.activities.startActivity
import splitties.alertdialog.appcompat.alert
import splitties.alertdialog.appcompat.message
import splitties.dimensions.dip
import splitties.views.centerText
import splitties.views.dsl.core.*
import splitties.views.gravityCenter
import splitties.views.textAppearance
import timber.log.Timber

class PermissionsExampleActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n") // This is an example where i18n matters less than readable code.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch {
            val calendarPermission = Manifest.permission.WRITE_CALENDAR
            while (!hasPermission(calendarPermission)) {
                try {
                    val quit = alert {
                        message = "We will ask for calendar permission. Don't grant it too soon " +
                                "if you want to test all cases from this sample!"
                    }.quitOrOk()
                    if (quit) {
                        finish(); return@launch
                    }
                    requestPermission(calendarPermission)
                    break
                } catch (e: PermissionDeniedException) {
                    val quit: Boolean = if (e.doNotAskAgain) {
                        alert {
                            message = "You denied the permission permanently.\n" +
                                    "You can grant it in the settings."
                        }.quitOrOk()
                    } else alert { message = "Please accept…" }.quitOrOk()
                    if (quit) {
                        finish(); return@launch
                    } else if (e.doNotAskAgain) {
                        startActivity(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) {
                            data = "package:$packageName".toUri()
                        }
                        Timber.d("Before yield")
                        yield()
                        Timber.d("After yield")
                        lifecycle.awaitState(Lifecycle.State.RESUMED)
                        Timber.d("After awaitState(RESUMED)")
                    }
                }
            }
            contentView = frameLayout {
                add(textView {
                    textAppearance = R.style.TextAppearance_AppCompat_Headline
                    text = "Thanks for granting the permission!\nNothing to see there now… :)"
                    centerText()
                }, lParams(gravity = gravityCenter) { margin = dip(16) })
            }
        }
    }

    private suspend fun AlertDialog.quitOrOk(): Boolean = showAndAwait(
        positiveButton = DialogButton.ok(false),
        negativeButton = DialogButton("Quit", true),
        dismissValue = false
    )
}
