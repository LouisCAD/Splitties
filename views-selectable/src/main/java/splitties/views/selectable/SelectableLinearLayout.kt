/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.views.selectable

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.annotation.CallSuper
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.LinearLayout
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

    @RequiresApi(LOLLIPOP)
    @CallSuper
    override fun dispatchDrawableHotspotChanged(x: Float, y: Float) {
        super.dispatchDrawableHotspotChanged(x, y)
        foregroundSelector?.setHotspot(x, y)
    }
}
