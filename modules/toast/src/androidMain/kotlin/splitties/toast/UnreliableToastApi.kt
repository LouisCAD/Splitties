/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.toast

@RequiresOptIn(
    message = "Toasts are never shown if notifications are disabled for your app, " +
        "and you cannot know in any way if a toast has actually been displayed or not. " +
        "Consider using a more reliable way to show information to the user such as " +
        "snackbars, banners or dialogs.",
    level = RequiresOptIn.Level.WARNING
)
annotation class UnreliableToastApi
