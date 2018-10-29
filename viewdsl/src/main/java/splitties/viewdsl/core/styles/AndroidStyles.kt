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
package splitties.viewdsl.core.styles

import android.widget.ProgressBar

object AndroidStyles {
    // TODO: Make inline when switching to Kotlin 1.3
    val progressBar = ProgressBarAndroidStyles(null)
}

// TODO: Make inline when switching to Kotlin 1.3
class ProgressBarAndroidStyles internal constructor(
        @Deprecated("", level = DeprecationLevel.HIDDEN) val nothing: Nothing?
) {
    @JvmField val smallSpinner = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleSmall)
    @JvmField val spinner = XmlStyle<ProgressBar>(android.R.attr.progressBarStyle)
    @JvmField val largeSpinner = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleLarge)
    @JvmField val horizontal = XmlStyle<ProgressBar>(android.R.attr.progressBarStyleHorizontal)
}
