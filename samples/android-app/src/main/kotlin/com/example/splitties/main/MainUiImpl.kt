/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.main

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import com.example.splitties.R
import splitties.dimensions.dip
import splitties.resources.dimenPxSize
import splitties.resources.styledColor
import splitties.views.coroutines.awaitOneClick
import splitties.views.coroutines.material.showAndAwaitOneClickThenHide
import splitties.views.dsl.appcompat.toolbar
import splitties.views.dsl.coordinatorlayout.anchorTo
import splitties.views.dsl.coordinatorlayout.appBarLParams
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.coordinatorlayout.defaultLParams
import splitties.views.dsl.core.*
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.dsl.material.EXIT_UNTIL_COLLAPSED
import splitties.views.dsl.material.MaterialComponentsStyles
import splitties.views.dsl.material.PIN
import splitties.views.dsl.material.SCROLL
import splitties.views.dsl.material.actionBarLParams
import splitties.views.dsl.material.appBarLayout
import splitties.views.dsl.material.collapsingToolbarLayout
import splitties.views.dsl.material.contentScrollingWithAppBarLParams
import splitties.views.dsl.material.defaultLParams
import splitties.views.dsl.material.floatingActionButton
import splitties.views.dsl.recyclerview.wrapInRecyclerView
import splitties.views.gravityCenterHorizontal
import splitties.views.gravityEndBottom
import splitties.views.imageResource
import splitties.views.margin
import splitties.views.material.contentScrimColor
import splitties.views.textResource
import com.google.android.material.R as MaterialR

class MainUiImpl(override val ctx: Context) : MainAndroidUi {

    override suspend fun awaitFabClick() = fab.showAndAwaitOneClickThenHide()
    override suspend fun awaitLaunchMaterialListDemoRequest() = launchDemoBtn.awaitOneClick()
    override suspend fun awaitLaunchPermissionDemoRequest() {
        bePoliteWithPermissionsBtn.awaitOneClick()
    }

    override suspend fun awaitLaunchSayHelloDemoRequest() {
        sayHelloBtn.awaitOneClick()
    }

    override suspend fun awaitToggleNightModeRequest() {
        toggleNightModeBtn.awaitOneClick()
    }

    override suspend fun awaitTrySoundRequest() {
        trySoundBtn.awaitOneClick()
    }

    private val materialStyles = MaterialComponentsStyles(ctx)
    private val materialButtons = materialStyles.button

    private val launchDemoBtn = materialButtons.text {
        textResource = R.string.go_to_the_demo
    }
    private val bePoliteWithPermissionsBtn = materialButtons.filled {
        textResource = R.string.be_polite_with_permissions
    }
    private val sayHelloBtn = materialButtons.text {
        textResource = R.string.say_hello
    }
    private val toggleNightModeBtn = materialButtons.filledWithIcon {
        setIconResource(R.drawable.ic_invert_colors_white_24dp)
        textResource = R.string.toggle_night_mode
    }
    private val trySoundBtn = materialButtons.filled {
        textResource = R.string.play_a_sound
    }
    private val fab = floatingActionButton {
        imageResource = R.drawable.ic_favorite_white_24dp
    }
    private val content = verticalLayout {
        add(launchDemoBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(bePoliteWithPermissionsBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(sayHelloBtn, lParams {
            gravity = gravityCenterHorizontal
            topMargin = dip(8)
        })
        add(toggleNightModeBtn, lParams {
            gravity = gravityCenterHorizontal
            bottomMargin = dip(8)
        })
        add(trySoundBtn, lParams {
            gravity = gravityCenterHorizontal
            bottomMargin = dip(8)
        })
        add(textView {
            textResource = R.string.large_text
        }, lParams {
            margin = dimenPxSize(R.dimen.text_margin)
        })
    }.wrapInRecyclerView()
    private val appBar = appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
        add(collapsingToolbarLayout {
            fitsSystemWindows = true
            contentScrimColor = styledColor(MaterialR.attr.colorPrimary)
            add(toolbar {
                (ctx as? AppCompatActivity)?.setSupportActionBar(this)
                popupTheme = R.style.AppTheme_PopupOverlay
            }, actionBarLParams(collapseMode = PIN))
        }, defaultLParams(height = matchParent) {
            scrollFlags = SCROLL or EXIT_UNTIL_COLLAPSED
        })
    }
    override val root = coordinatorLayout {
        fitsSystemWindows = true
        add(appBar, appBarLParams(dip(180)))
        add(content, contentScrollingWithAppBarLParams())
        add(fab, defaultLParams {
            anchorTo(appBar, gravity = gravityEndBottom)
            margin = dip(16)
        })
    }
}

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class MainUiImplPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = { MainUiImpl(it) }
)
//endregion
