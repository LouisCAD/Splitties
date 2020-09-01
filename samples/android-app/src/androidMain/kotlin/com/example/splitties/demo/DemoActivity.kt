/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.splitties.R
import splitties.checkedlazy.mainThreadLazy
import splitties.snackbar.action
import splitties.snackbar.onDismiss
import splitties.snackbar.snack
import splitties.snackbar.snackForever
import splitties.views.appcompat.configActionBar
import splitties.views.dsl.core.setContentView
import splitties.views.onClick

class DemoActivity : AppCompatActivity(), DemoUi.Host {

    private val ui by mainThreadLazy { DemoUi(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui)
        configActionBar(homeAsUp = true)
        ui.fab.onClick {
            ui.root.snack(R.string.title_feature_not_available) {
                onDismiss {
                    ui.root.snackForever(R.string.msg_go_to_pc_manually)
                }
            }
        }
    }

    override fun onDemoItemClicked(demoItem: DemoItem) {
        ui.root.snackForever(R.string.msg_marketing_guy_invents_new_feature) {
            action(R.string.scroll_to_the_end) {
                ui.demoListView.scrollToPosition(Int.MAX_VALUE - 1)
            }
        }
    }
}
