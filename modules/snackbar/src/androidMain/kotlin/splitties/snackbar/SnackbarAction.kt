/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.snackbar

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

inline fun Snackbar.action(text: CharSequence, crossinline onClick: () -> Unit) {
    setAction(text) { onClick() }
}

inline fun Snackbar.action(@StringRes textResId: Int, crossinline onClick: () -> Unit) {
    setAction(textResId) { onClick() }
}

inline fun Snackbar.action(
    text: CharSequence,
    @ColorInt textColor: Int,
    crossinline onClick: () -> Unit
) {
    setActionTextColor(textColor)
    action(text, onClick)
}

inline fun Snackbar.action(
    @StringRes textResId: Int,
    @ColorInt textColor: Int,
    crossinline onClick: () -> Unit
) {
    setActionTextColor(textColor)
    action(textResId, onClick)
}

inline fun Snackbar.action(
    text: CharSequence,
    textColor: ColorStateList,
    crossinline onClick: () -> Unit
) {
    setActionTextColor(textColor)
    action(text, onClick)
}

inline fun Snackbar.action(
    @StringRes textResId: Int,
    textColor: ColorStateList,
    crossinline onClick: () -> Unit
) {
    setActionTextColor(textColor)
    action(textResId, onClick)
}
