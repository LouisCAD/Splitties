/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

/**
 * **Requires** your project to depend on kotlinx.coroutines
 *
 * ## IMPORTANT:
 * Make sure that your `Preferences` subclass **constructor is private**!
 *
 * This class is meant to be a subclass of the **`companion object`** of your [Preferences]
 * subclass.
 *
 * It allows to always load the underlying [SharedPreferences] and its associated xml file off the
 * main/UI thread using **constructor-like syntax**.
 */
@ExperimentalSplittiesApi
abstract class SuspendPrefsAccessor<out Prefs : Preferences>(prefsConstructorRef: () -> Prefs) {
    suspend operator fun invoke(): Prefs = if (prefDelegate.isInitialized()) {
        prefDelegate.value
    } else withContext(Dispatchers.IO) { prefDelegate.value }

    private val prefDelegate = lazy(prefsConstructorRef)
}
