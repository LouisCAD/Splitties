/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties

import splitties.init.AppCtxInitProvider
import splitties.init.appCtx
import splitties.initprovider.InitProvider

/**
 * Just present here and in the manifest to call [AppCtxInitProvider] implementation
 * for the second process of this app so [appCtx] is initialized.
 * @see AppCtxInitProvider
 */
class SecondProcessInitProvider : InitProvider() {
    override fun onCreate() = true.also { _ -> AppInit }
}
