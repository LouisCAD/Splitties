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
package splitties.preferences.experimental

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.withContext
import splitties.preferences.Preferences

/**
 * **Requires** you have the following dependency in your project:
 * `org.jetbrains.kotlinx:kotlinx-coroutines-android:0.27.0`
 */
abstract class SuspendPrefsAccessor<out Prefs : Preferences>(prefsConstructorRef: () -> Prefs) {
    suspend operator fun invoke(): Prefs = if (prefDelegate.isInitialized()) {
        prefDelegate.value
    } else withContext(Dispatchers.IO) { prefDelegate.value }

    private val prefDelegate = lazy(prefsConstructorRef)
}
