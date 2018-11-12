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
package com.louiscad.splittiessample.extensions.ui

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import com.louiscad.splittiessample.R
import splitties.views.appcompat.configActionBar
import splitties.views.appcompat.showHomeAsUp
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.core.add
import splitties.views.dsl.design.appBarLParams
import splitties.views.dsl.design.appBarLayout
import splitties.views.dsl.design.defaultLParams

fun CoordinatorLayout.addDefaultAppBar(ctx: Context) {
    add(appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
        add(toolbar {
            popupTheme = R.style.AppTheme_PopupOverlay
            val activity = ctx as? AppCompatActivity ?: return@toolbar
            activity.setSupportActionBar(this)
            activity.configActionBar { showHomeAsUp = true }
        }, defaultLParams())
    }, appBarLParams())
}
