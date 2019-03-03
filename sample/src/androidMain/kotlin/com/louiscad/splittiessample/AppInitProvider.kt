/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.louiscad.splittiessample

import splitties.initprovider.InitProvider

class AppInitProvider : InitProvider() {
    override fun onCreate() = true.also { _ -> AppInit }
}
