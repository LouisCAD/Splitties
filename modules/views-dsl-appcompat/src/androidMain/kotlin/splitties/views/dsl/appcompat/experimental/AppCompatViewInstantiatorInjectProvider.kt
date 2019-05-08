/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.appcompat.experimental

import splitties.experimental.InternalSplittiesApi
import splitties.initprovider.InitProvider
import splitties.views.dsl.core.experimental.ViewFactoryImpl

@InternalSplittiesApi
class AppCompatViewInstantiatorInjectProvider : InitProvider() {
    override fun onCreate() = ViewFactoryImpl.appInstance.apply {
        add(::instantiateAppCompatView)
        addForThemeAttrStyled(::instantiateThemeAttrStyledAppCompatView)
    }.let { true }
}
