/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
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
