/*
 * Copyright 2019-2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.permissions

import android.Manifest
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.example.splitties.extensions.permissions.ensureAllPermissions
import kotlinx.coroutines.*
import splitties.dimensions.dip
import splitties.snackbar.longSnack
import splitties.views.centerText
import splitties.views.dsl.core.add
import splitties.views.dsl.core.button
import splitties.views.dsl.core.contentView
import splitties.views.dsl.core.lParams
import splitties.views.margin
import splitties.views.dsl.core.textView
import splitties.views.dsl.core.verticalLayout
import splitties.views.gravityCenter
import splitties.views.onClick
import splitties.views.textAppearance
import androidx.appcompat.R as AppCompatR

class PermissionsExampleActivity : AppCompatActivity() {

    @Suppress("SetTextI18n") // This is just an example for developers.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch {
            ensureAllPermissions(
                permissions = buildList {
                    if (SDK_INT >= 33) add(Manifest.permission.POST_NOTIFICATIONS)
                    add(Manifest.permission.WRITE_CALENDAR)
                },
                askDialogTitle = "Calendar permission required",
                askDialogMessage = "We will ask for calendar permission.\n" +
                    "Don't grant it too soon if you want to test all cases from this sample!"
            ) {
                finish()
                awaitCancellation()
            }
            contentView = verticalLayout {
                gravity = gravityCenter
                add(textView {
                    textAppearance = AppCompatR.style.TextAppearance_AppCompat_Headline
                    text = "Thanks for granting the permission!\n" +
                        "Nothing to see there now… :)"
                    centerText()
                }, lParams { margin = dip(16) })
                if (SDK_INT >= 33) add(button {
                    text = "Revoke write calendar permission on next kill"
                    onClick {
                        revokeSelfPermissionOnKill(Manifest.permission.WRITE_CALENDAR)
                        longSnack("Permission will be revoked on next kill")
                    }
                }, lParams())
            }
        }
    }
}
