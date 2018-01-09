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

package splitties.snackbar

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar

inline fun Snackbar.action(text: CharSequence, crossinline onClick: () -> Unit) {
    setAction(text) { onClick() }
}

inline fun Snackbar.action(@StringRes textResId: Int, crossinline onClick: () -> Unit) {
    setAction(textResId) { onClick() }
}

inline fun Snackbar.action(text: CharSequence,
                           @ColorInt textColor: Int,
                           crossinline onClick: () -> Unit) {
    setActionTextColor(textColor)
    action(text, onClick)
}

inline fun Snackbar.action(@StringRes textResId: Int,
                           @ColorInt textColor: Int,
                           crossinline onClick: () -> Unit) {
    setActionTextColor(textColor)
    action(textResId, onClick)
}

inline fun Snackbar.action(text: CharSequence,
                           textColor: ColorStateList,
                           crossinline onClick: () -> Unit) {
    setActionTextColor(textColor)
    action(text, onClick)
}

inline fun Snackbar.action(@StringRes textResId: Int,
                           textColor: ColorStateList,
                           crossinline onClick: () -> Unit) {
    setActionTextColor(textColor)
    action(textResId, onClick)
}
