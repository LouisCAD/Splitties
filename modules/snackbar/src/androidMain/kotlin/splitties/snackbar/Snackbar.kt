/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.snackbar

import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import splitties.resources.txt

/**
 * Shows a [Snackbar].
 * You can configure the [action] with its text and color in [actionSetup] if needed.
 *
 * @param msg     The text to show. Can be formatted text.
 * @param duration Either [Snackbar.LENGTH_SHORT] (default), [Snackbar.LENGTH_LONG] or [Snackbar.LENGTH_INDEFINITE]
 */
inline fun CoordinatorLayout.snack(
    msg: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionSetup: Snackbar.() -> Unit = {}
) = Snackbar.make(this, msg, duration).apply(actionSetup).also { it.show() }

/**
 * Shows a [Snackbar].
 * You can configure the [action] with its text and color in [actionSetup] if needed.
 *
 * @param msgResId The resource id of the text to show. Can be formatted text.
 * @param duration Either [Snackbar.LENGTH_SHORT] (default), [Snackbar.LENGTH_LONG] or [Snackbar.LENGTH_INDEFINITE]
 */
inline fun CoordinatorLayout.snack(
    @StringRes msgResId: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionSetup: Snackbar.() -> Unit = {}
) = snack(txt(msgResId), duration, actionSetup)

/**
 * Alias for calling [snack] with [Snackbar.LENGTH_LONG] as a duration parameter.
 */
inline fun CoordinatorLayout.longSnack(
    msg: CharSequence,
    actionSetup: Snackbar.() -> Unit = {}
) = snack(msg, Snackbar.LENGTH_LONG, actionSetup)

/**
 * Alias for calling [snack] with [Snackbar.LENGTH_LONG] as a duration parameter.
 */
inline fun CoordinatorLayout.longSnack(
    @StringRes msgResId: Int,
    actionSetup: Snackbar.() -> Unit = {}
) = longSnack(txt(msgResId), actionSetup)

/**
 * Alias for calling [snack] with [Snackbar.LENGTH_INDEFINITE] as a duration parameter.
 *
 * **Watch your weight!**
 *
 * Side note: This method could have get one of the following names instead:
 * - `snackForLife(…)`
 * - `snackOfYourLife(…)`
 * - `outToLunch(…)`
 * - `pinnedSnack(…)` (this one was **not fun**, so it was immediately discarded)
 * - `infiniteSnack(…)`
 * - `indefiniteSnack(…)` (probably the most accurate naming, but we didn't find it any fun either)
 * - `allYouCanEatBuffet(…)`
 * - `buffet(…)`
 */
inline fun CoordinatorLayout.snackForever(
    msg: CharSequence,
    actionSetup: Snackbar.() -> Unit = {}
) = snack(msg, Snackbar.LENGTH_INDEFINITE, actionSetup)

/**
 * Alias for calling [snack] with [Snackbar.LENGTH_INDEFINITE] as a duration parameter.
 *
 * **Watch your weight!**
 *
 * Side note: This method could have get one of the following names instead:
 * - `snackForLife(…)`
 * - `snackOfYourLife(…)`
 * - `outToLunch(…)`
 * - `pinnedSnack(…)` (this one was **not fun**, so it was immediately discarded)
 * - `infiniteSnack(…)`
 * - `indefiniteSnack(…)` (probably the most accurate naming, but we didn't find it any fun either)
 * - `allYouCanEatBuffet(…)`
 * - `buffet(…)`
 */
inline fun CoordinatorLayout.snackForever(
    @StringRes msgResId: Int,
    actionSetup: Snackbar.() -> Unit = {}
) = snackForever(txt(msgResId), actionSetup)
