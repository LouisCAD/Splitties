/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.demo

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import com.example.splitties.R
import splitties.dimensions.dip
import splitties.resources.txt
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.coordinatorlayout.appBarLParams
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.coordinatorlayout.defaultLParams
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.add
import splitties.views.margin
import splitties.views.dsl.core.withTheme
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.dsl.material.*
import splitties.views.dsl.recyclerview.recyclerView
import splitties.views.gravityEndBottom
import splitties.views.imageResource
import splitties.views.setPaddingDp

class DemoUi(override val ctx: Context, host: Host) : Ui {
    interface Host : DemoAdapter.DemoViewHolder.Host

    val demoListView = recyclerView(R.id.recycler_view) {
        clipToPadding = false
        setPaddingDp(top = 8)
        setHasFixedSize(true)
        val demoAdapter = DemoAdapter(host)
        adapter = demoAdapter
        layoutManager = demoAdapter.layoutManager
    }
    val fab = floatingActionButton {
        imageResource = R.drawable.ic_computer_white_24dp
    }
    override val root = coordinatorLayout {
        fitsSystemWindows = true
        add(appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
            add(toolbar {
                subtitle = txt(R.string.subtitle_items_count_hint)
                popupTheme = R.style.AppTheme_PopupOverlay
                (ctx as? AppCompatActivity)?.setSupportActionBar(this)
            }, defaultLParams())
        }, appBarLParams())
        add(demoListView, contentScrollingWithAppBarLParams())
        add(fab, defaultLParams(gravity = gravityEndBottom) {
            margin = dip(16)
        })
    }
}

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class DemoUiPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = {
        DemoUi(it, object : DemoUi.Host {
            override fun onDemoItemClicked(demoItem: DemoItem) = Unit
        })
    }
)
//endregion
