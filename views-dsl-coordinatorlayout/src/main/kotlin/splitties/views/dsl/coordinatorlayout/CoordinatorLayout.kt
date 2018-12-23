@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.coordinatorlayout

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import splitties.views.dsl.core.*
import splitties.views.existingOrNewId
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun CoordinatorLayout.defaultLParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    gravity: Int = Gravity.NO_GRAVITY,
    initParams: CoordinatorLayout.LayoutParams.() -> Unit = {}
): CoordinatorLayout.LayoutParams = CoordinatorLayout.LayoutParams(width, height).also {
    it.gravity = gravity
}.apply(initParams)

inline fun CoordinatorLayout.appBarLParams(
    height: Int = wrapContent,
    initParams: CoordinatorLayout.LayoutParams.() -> Unit = {}
): CoordinatorLayout.LayoutParams = defaultLParams(
    width = matchParent,
    height = height,
    initParams = initParams
)

inline fun CoordinatorLayout.LayoutParams.anchorTo(view: View, gravity: Int = Gravity.NO_GRAVITY) {
    anchorId = view.existingOrNewId
    anchorGravity = gravity
}

inline fun Context.coordinatorLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CoordinatorLayout.() -> Unit = {}
): CoordinatorLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::CoordinatorLayout, id, theme, initView)
}

inline fun View.coordinatorLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CoordinatorLayout.() -> Unit = {}
): CoordinatorLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return context.coordinatorLayout(id, theme, initView)
}

inline fun Ui.coordinatorLayout(
    @IdRes id: Int = View.NO_ID,
    @StyleRes theme: Int = NO_THEME,
    initView: CoordinatorLayout.() -> Unit = {}
): CoordinatorLayout {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return ctx.coordinatorLayout(id, theme, initView)
}
