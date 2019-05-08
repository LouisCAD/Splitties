/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.appcompat

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlin.DeprecationLevel.HIDDEN

@SuppressLint("LogNotTimber") // Timber is not a dependency here, but lint passes through.
inline fun AppCompatActivity.configActionBar(config: ActionBar.() -> Unit) {
    supportActionBar?.config() ?: Log.wtf(
        "ActionBar",
        "No ActionBar in this Activity! Config skipped.",
        AssertionError()
    )
}

inline var ActionBar.showTitle: Boolean
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setDisplayShowTitleEnabled(value)

inline var ActionBar.showHome: Boolean
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setDisplayShowHomeEnabled(value)

inline var ActionBar.showHomeAsUp: Boolean
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setDisplayHomeAsUpEnabled(value)

inline var ActionBar.useLogo: Boolean
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setDisplayUseLogoEnabled(value)

inline var ActionBar.showCustomView: Boolean
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setDisplayShowCustomEnabled(value)
