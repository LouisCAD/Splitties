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

// TODO: Make all properties inline when switching to Kotlin 1.3
object AndroidStyles {
    val progressBar = ProgressBarAndroidStyles(null)
    val ratingBar = RatingBarAndroidStyles(null)
    val button = ButtonAndroidStyles(null)
}

// TODO: Make inline when switching to Kotlin 1.3
class ProgressBarAndroidStyles internal constructor(
        @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    @JvmField val small = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleSmall)
    @JvmField val default = XmlStyle<ProgressBar>(android.R.attr.progressBarStyle)
    @JvmField val large = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleLarge)
    @JvmField val horizontal = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleHorizontal)
}

// TODO: Make inline when switching to Kotlin 1.3
class RatingBarAndroidStyles internal constructor(
        @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    @JvmField val small = XmlStyle<RatingBar>(android.R.attr.ratingBarStyleSmall)
    @JvmField val default = XmlStyle<RatingBar>(android.R.attr.ratingBarStyle)
    @JvmField val indicator = XmlStyle<RatingBar>(android.R.attr.ratingBarStyleIndicator)
}

// TODO: Make inline when switching to Kotlin 1.3
class ButtonAndroidStyles internal constructor(
        @Deprecated("Still required for inline classes", level = HIDDEN) val nothing: Nothing?
) {
    /** Small Button style. */
    @JvmField val small = XmlStyle<Button>(android.R.attr.buttonStyleSmall)
    /** Normal Button style. */
    @JvmField val default = XmlStyle<Button>(android.R.attr.buttonStyle)
    /** Style for buttons without an explicit border, often used in groups. */
    @JvmField val borderless = XmlStyle<Button>(android.R.attr.borderlessButtonStyle)
    /** Button style to inset into an EditText. */
    @JvmField val inset = XmlStyle<Button>(android.R.attr.buttonStyleInset)
}
