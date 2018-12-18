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
package splitties.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * **Requires** you have the following dependency in your project:
 * `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.0.1`
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
abstract class SuspendPrefsAccessor<out Prefs : Preferences>(prefsConstructorRef: () -> Prefs) {
    suspend operator fun invoke(): Prefs = if (prefDelegate.isInitialized()) {
        prefDelegate.value
    } else withContext(Dispatchers.IO) { prefDelegate.value }

    private val prefDelegate = lazy(prefsConstructorRef)
}
