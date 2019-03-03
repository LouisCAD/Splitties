/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.material.experimental

import splitties.experimental.InternalSplittiesApi
import splitties.initprovider.InitProvider
import splitties.views.dsl.core.experimental.ViewFactoryImpl

@InternalSplittiesApi
class MaterialViewInstantiatorInjectProvider : InitProvider() {
    override fun onCreate(): Boolean {
        ViewFactoryImpl.appInstance.apply {
            add(::instantiateMaterialView)
            addForThemeAttrStyled(::instantiateThemeAttrStyledMaterialView)
        }
        return true
    }
}
