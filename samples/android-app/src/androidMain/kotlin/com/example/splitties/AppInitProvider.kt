/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties

import splitties.initprovider.InitProvider
import splitties.initprovider.ObsoleteContentProviderHack

@OptIn(ObsoleteContentProviderHack::class)
class AppInitProvider : InitProvider() {
    override fun onCreate() = true.also { _ -> AppInit }
}
