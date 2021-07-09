/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.material.styles

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import com.google.android.material.chip.Chip
import splitties.views.dsl.core.NO_THEME
import splitties.views.dsl.core.styles.styledView
import splitties.views.dsl.material.R
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@JvmInline
value class ChipStyles @PublishedApi internal constructor(
    @PublishedApi internal val ctx: Context
) {
    inline fun action(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Chip.() -> Unit = {}
    ): Chip {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return ctx.styledView(
            newViewRef = ::Chip,
            styleAttr = R.attr.Widget_MaterialComponents_Chip_Action,
            id = id,
            theme = theme,
            initView = initView
        )
    }

    inline fun choice(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Chip.() -> Unit = {}
    ): Chip {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return ctx.styledView(
            newViewRef = ::Chip,
            styleAttr = R.attr.Widget_MaterialComponents_Chip_Choice,
            id = id,
            theme = theme,
            initView = initView
        )
    }

    inline fun entry(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Chip.() -> Unit = {}
    ): Chip {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return ctx.styledView(
            newViewRef = ::Chip,
            styleAttr = R.attr.Widget_MaterialComponents_Chip_Entry,
            id = id,
            theme = theme,
            initView = initView
        )
    }

    inline fun filter(
        @IdRes id: Int = View.NO_ID,
        @StyleRes theme: Int = NO_THEME,
        initView: Chip.() -> Unit = {}
    ): Chip {
        contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
        return ctx.styledView(
            newViewRef = ::Chip,
            styleAttr = R.attr.Widget_MaterialComponents_Chip_Filter,
            id = id,
            theme = theme,
            initView = initView
        )
    }
}
