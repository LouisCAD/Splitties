/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.example.splitties.extensions.permissions.ensurePermissionOrFinishAndCancel
import kotlinx.coroutines.launch
import splitties.dimensions.dip
import splitties.views.centerText
import splitties.views.dsl.core.add
import splitties.views.dsl.core.contentView
import splitties.views.dsl.core.frameLayout
import splitties.views.dsl.core.lParams
import splitties.views.dsl.core.margin
import splitties.views.dsl.core.textView
import splitties.views.gravityCenter
import splitties.views.textAppearance
import androidx.appcompat.R as AppCompatR

class PermissionsExampleActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n") // This is an example where i18n matters less than readable code.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch {
            ensurePermissionOrFinishAndCancel(
                permission = Manifest.permission.WRITE_CALENDAR,
                askDialogTitle = "Calendar permission required",
                askDialogMessage = "We will ask for calendar permission.\n" +
                        "Don't grant it too soon if you want to test all cases from this sample!"
            )
            contentView = frameLayout {
                add(textView {
                    textAppearance = AppCompatR.style.TextAppearance_AppCompat_Headline
                    text = "Thanks for granting the permission!\n" +
                            "Nothing to see there now… :)"
                    centerText()
                }, lParams(gravity = gravityCenter) { margin = dip(16) })
            }
        }
    }
}
