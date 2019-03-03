/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.core.styles

import android.view.View
import androidx.annotation.AttrRes

@Suppress("unused") // Type parameter is used externally for type inference
inline class XmlStyle<in V : View>(@AttrRes val styleAttr: Int)
