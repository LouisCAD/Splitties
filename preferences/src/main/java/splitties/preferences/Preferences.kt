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

package splitties.preferences

import android.content.Context
import splitties.init.appCtx

abstract class Preferences(ctx: Context = appCtx, name: String, mode: Int = Context.MODE_PRIVATE) {
    private val prefs = ctx.getSharedPreferences(name, mode)

    operator fun contains(o: Any) = prefs === o

    protected inner class BoolPref(key: String, defaultValue: Boolean)
        : BoolPrefField(prefs, key, defaultValue)

    protected inner class IntPref(key: String, defaultValue: Int)
        : IntPrefField(prefs, key, defaultValue)

    protected inner class FloatPref(key: String, defaultValue: Float)
        : FloatPrefField(prefs, key, defaultValue)

    protected inner class LongPref(key: String, defaultValue: Long)
        : LongPrefField(prefs, key, defaultValue)

    protected inner class StringPref(key: String, defaultValue: String? = null)
        : StringPrefField(prefs, key, defaultValue)

    protected inner class StringSetPref(key: String, defaultValue: MutableSet<String>? = null)
        : StringSetPrefField(prefs, key, defaultValue)
}
