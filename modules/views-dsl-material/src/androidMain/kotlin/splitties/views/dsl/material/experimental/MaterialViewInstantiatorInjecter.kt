/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.material.experimental

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import splitties.views.dsl.core.experimental.*

@Keep // Needed because there's no default R8/proguard rules for App Startup Initializers.
@Suppress("unused") // False positive. Used in the manifest, but tooling analysis is incomplete.
internal class MaterialViewInstantiatorInjecter : Initializer<MaterialViewInstantiatorInjecter> {

    override fun create(context: Context) = apply {
        ViewFactoryImpl.appInstance.apply {
            add(::instantiateMaterialView)
            addForThemeAttrStyled(::instantiateThemeAttrStyledMaterialView)
        }
    }

    override fun dependencies() = emptyList<Nothing>()
}
