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
package splitties.views.dsl.core.styles

import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import kotlin.DeprecationLevel.HIDDEN

object AndroidStyles {
    inline val progressBar get() = ProgressBarAndroidStyles(null)
    inline val ratingBar get() = RatingBarAndroidStyles(null)
    inline val button get() = ButtonAndroidStyles(null)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ProgressBarAndroidStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    inline val small get() = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleSmall)
    inline val default get() = XmlStyle<ProgressBar>(android.R.attr.progressBarStyle)
    inline val large get() = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleLarge)
    inline val horizontal get() = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleHorizontal)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class RatingBarAndroidStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    inline val small get() = XmlStyle<RatingBar>(android.R.attr.ratingBarStyleSmall)
    inline val default get() = XmlStyle<RatingBar>(android.R.attr.ratingBarStyle)
    inline val indicator get() = XmlStyle<RatingBar>(android.R.attr.ratingBarStyleIndicator)
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class ButtonAndroidStyles @PublishedApi internal constructor(
    @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    /** Small Button style. */
    inline val small get() = XmlStyle<Button>(android.R.attr.buttonStyleSmall)
    /** Normal Button style. */
    inline val default get() = XmlStyle<Button>(android.R.attr.buttonStyle)
    /** Style for buttons without an explicit border, often used in groups. */
    inline val borderless get() = XmlStyle<Button>(android.R.attr.borderlessButtonStyle)
    /** Button style to inset into an EditText. */
    inline val inset get() = XmlStyle<Button>(android.R.attr.buttonStyleInset)
}
