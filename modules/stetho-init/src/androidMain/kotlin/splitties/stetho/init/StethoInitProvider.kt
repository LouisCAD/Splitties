/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.stetho.init

import com.facebook.stetho.Stetho

import splitties.initprovider.InitProvider

class StethoInitProvider : InitProvider() {

    override fun onCreate(): Boolean {
        Stetho.initializeWithDefaults(context!!)
        return true
    }
}
