/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.selectable

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import splitties.resources.styledDrawable

/**
 * [LinearLayout] with ripple effect / select foreground when touched.
 */
open class SelectableLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)

    var foregroundSelector: Drawable? = null
        set(value) {
            field?.callback = null
            field = value
            value?.callback = this
            setWillNotDraw(value === null)
        }

    init {
        foregroundSelector = styledDrawable(android.R.attr.selectableItemBackground)
    }

    @CallSuper
    override fun drawableStateChanged() {
        super.drawableStateChanged()
        foregroundSelector?.state = drawableState
    }

    @CallSuper
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        foregroundSelector?.setBounds(0, 0, w, h)
    }

    @CallSuper
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        foregroundSelector?.draw(canvas)
    }

    @CallSuper
    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        foregroundSelector?.jumpToCurrentState()
    }

    @CallSuper
    override fun verifyDrawable(who: Drawable): Boolean {
        return who === foregroundSelector || super.verifyDrawable(who)
    }

    @RequiresApi(21)
    @CallSuper
    override fun dispatchDrawableHotspotChanged(x: Float, y: Float) {
        super.dispatchDrawableHotspotChanged(x, y)
        foregroundSelector?.setHotspot(x, y)
    }
}
