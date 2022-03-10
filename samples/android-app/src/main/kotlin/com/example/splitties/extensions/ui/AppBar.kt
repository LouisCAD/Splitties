/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.extensions.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.splitties.R
import splitties.views.appcompat.configActionBar
import splitties.views.appcompat.homeAsUp
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.core.add
import splitties.views.dsl.coordinatorlayout.appBarLParams
import splitties.views.dsl.material.appBarLayout
import splitties.views.dsl.coordinatorlayout.defaultLParams

fun CoordinatorLayout.addDefaultAppBar(ctx: Context) {
    add(appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
        add(toolbar {
            popupTheme = R.style.AppTheme_PopupOverlay
            val activity = ctx as? AppCompatActivity ?: return@toolbar
            activity.setSupportActionBar(this)
            activity.configActionBar { homeAsUp = true }
        }, defaultLParams())
    }, appBarLParams())
}
