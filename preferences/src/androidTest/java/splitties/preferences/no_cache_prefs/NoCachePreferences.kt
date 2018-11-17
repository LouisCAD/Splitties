/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package splitties.preferences.no_cache_prefs

import android.content.Context
import splitties.init.appCtx

abstract class NoCachePreferences(
    ctx: Context = appCtx,
    name: String,
    mode: Int = Context.MODE_PRIVATE
) {
    private val prefs = ctx.getSharedPreferences(name, mode)

    operator fun contains(o: Any) = prefs === o

    internal inner class PBoolPref(key: String, defaultValue: Boolean) :
        PBoolPrefField(prefs, key, defaultValue)

    internal inner class PIntPref(key: String, defaultValue: Int) :
        PIntPrefField(prefs, key, defaultValue)

    internal inner class PFloatPref(key: String, defaultValue: Float) :
        PFloatPrefField(prefs, key, defaultValue)

    internal inner class PLongPref(key: String, defaultValue: Long) :
        PLongPrefField(prefs, key, defaultValue)

    internal inner class PStringPref(key: String, defaultValue: String) :
        PStringPrefField(prefs, key, defaultValue)
}
