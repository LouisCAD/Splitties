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
package splitties.viewdsl.appcompat.styles

import android.content.res.Resources
import android.widget.Button
import splitties.viewdsl.core.Ui
import splitties.viewdsl.core.styles.XmlStyle

@Suppress("FunctionName")
fun Ui.AppCompatStyles(): AppCompatStyles {
    AppCompatStyles.loadInto(ctx.theme)
    return AppCompatStyles.instance
}

class AppCompatStyles private constructor() {
    //TODO: Replace @JvmField with inline val when switching to Kotlin 1.3
    val button = ButtonAppCompatStyles()

    class ButtonAppCompatStyles internal constructor() {
        @JvmField val default = XmlStyle<Button>(R.attr.Widget_AppCompat_Button)
        @JvmField val colored = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Colored)
        @JvmField val flat = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Borderless)
        @JvmField
        val flatColored = XmlStyle<Button>(R.attr.Widget_AppCompat_Button_Borderless_Colored)
        inline val borderlessColored get() = flatColored
    }

    companion object {
        internal val instance = AppCompatStyles()
        @Suppress("NOTHING_TO_INLINE")
        internal inline fun loadInto(theme: Resources.Theme) {
            theme.applyStyle(R.style.AppCompatStyles, false)
        }
    }
}
