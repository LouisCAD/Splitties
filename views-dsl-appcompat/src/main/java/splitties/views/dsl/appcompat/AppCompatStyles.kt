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
package splitties.views.dsl.appcompat

import android.content.Context
import android.content.res.Resources
import android.widget.*
import splitties.views.dsl.core.styles.XmlStyle
import kotlin.DeprecationLevel.HIDDEN

@Suppress("FunctionName")
fun AppCompatStyles(ctx: Context): AppCompatStyles {
    AppCompatStyles.loadInto(ctx.theme)
    return AppCompatStyles.instance
}

class AppCompatStyles private constructor() {
    inline val button get() = ButtonAppCompatStyles(null)
    inline val seekBar get() = SeekBarAppCompatStyles(null)
    inline val spinner get() = SpinnerAppCompatStyles(null)
    inline val textView get() = TextViewAppCompatStyles(null)
    inline val actionButton get() = XmlStyle<ImageButton>(R.attr.Widget_AppCompat_ActionButton)

    companion object {
        internal val instance = AppCompatStyles()
        @Suppress("NOTHING_TO_INLINE")
        internal inline fun loadInto(theme: Resources.Theme) {
            theme.applyStyle(R.style.AppCompatStyles, false)
        }
    }
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ButtonAppCompatStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    inline val default get() = XmlStyle<Button>(R.attr.Widget_AppCompat_Button)
    inline val colored get() = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Colored)
    inline val flat get() = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Borderless)
    inline val borderless get() = flat
    inline val flatColored get() = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Borderless_Colored)
    inline val borderlessColored get() = flatColored
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class TextViewAppCompatStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    inline val spinnerItem get() = XmlStyle<TextView>(R.attr.Widget_AppCompat_TextView_SpinnerItem)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class SeekBarAppCompatStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    inline val default get() = XmlStyle<SeekBar>(R.attr.Widget_AppCompat_SeekBar)
    inline val discrete get() = XmlStyle<SeekBar>(R.attr.Widget_AppCompat_SeekBar_Discrete)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class SpinnerAppCompatStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    inline val default get() = XmlStyle<Spinner>(R.attr.Widget_AppCompat_Spinner)
    inline val underlined get() = XmlStyle<Spinner>(R.attr.Widget_AppCompat_Spinner_Underlined)
}
