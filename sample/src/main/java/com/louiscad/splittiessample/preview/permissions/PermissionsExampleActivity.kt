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
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.core.view.isGone
import com.louiscad.splittiessample.R
import com.louiscad.splittiessample.extensions.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import splitties.dimensions.dip
import splitties.toast.longToast
import splitties.toast.toast
import splitties.views.centerText
import splitties.views.dsl.core.*
import splitties.views.gravityCenter
import splitties.views.textAppearance

class PermissionsExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = frameLayout {
            add(textView {
                textAppearance = R.style.TextAppearance_AppCompat_Headline
                text = "May we have the right to write in your calendar?"
                centerText()
            }, lParams(gravity = gravityCenter) { margin = dip(16) })
        }
        contentView = view
        lifecycle.coroutineScope.launch {
            val calendarPermission = Manifest.permission.WRITE_CALENDAR
            if (!hasPermission(calendarPermission)) while (isActive) {
                try {
                    delay(1000)
                    requestPermission(calendarPermission)
                    break
                } catch (e: PermissionDeniedException) {
                    if (e.doNotAskAgain) {
                        longToast(
                            "You denied the permission permanently.\n" +
                                    "You can grant it in the settings."
                        )
                        delay(3000); toast("Ciao!"); finish(); return@launch
                    } else {
                        longToast("Please accept…"); delay(2000)
                    }
                }
            }
            view.isGone = true
            longToast("Thanks for granting the permission!\nNothing to see there now… :)")
        }
    }
}
