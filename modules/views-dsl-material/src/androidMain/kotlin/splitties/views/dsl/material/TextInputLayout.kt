/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.material

import androidx.annotation.IdRes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import splitties.views.dsl.core.add
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.view
import splitties.views.dsl.core.wrapContent
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import android.widget.LinearLayout.LayoutParams as LP

inline fun TextInputLayout.addInput(
    @IdRes id: Int,
    initView: TextInputEditText.() -> Unit = {}
): TextInputEditText {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return add(
        view(
            createView = ::TextInputEditText,
            id = id,
            initView = initView
        ), LP(matchParent, wrapContent)
    )
}
