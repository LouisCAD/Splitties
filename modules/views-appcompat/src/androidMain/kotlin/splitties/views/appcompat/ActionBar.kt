/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.appcompat

import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import splitties.bitflags.hasFlag
import splitties.bitflags.minusFlag
import splitties.bitflags.withFlag

@Suppress("NOTHING_TO_INLINE") // This overload is optimized for the most common use case.
inline fun AppCompatActivity.configActionBar(
    homeAsUp: Boolean = supportActionBar?.homeAsUp ?: false
) {
    supportActionBar?.also {
        it.homeAsUp = homeAsUp
    } ?: Log.wtf(
        "ActionBar",
        "No ActionBar in this Activity! Config skipped.",
        AssertionError()
    )
}

fun AppCompatActivity.configActionBar(
    homeAsUp: Boolean = supportActionBar?.homeAsUp ?: false,
    showTitle: Boolean = supportActionBar?.showTitle ?: false,
    showHome: Boolean = supportActionBar?.showHome ?: false,
    useLogo: Boolean = supportActionBar?.useLogo ?: false,
    showCustomView: Boolean = supportActionBar?.showCustomView ?: false
) {
    supportActionBar?.also {
        it.homeAsUp = homeAsUp
        it.showTitle = showTitle
        it.showHome = showHome
        it.useLogo = useLogo
        it.showCustomView = showCustomView
    } ?: Log.wtf(
        "ActionBar",
        "No ActionBar in this Activity! Config skipped.",
        AssertionError()
    )
}

inline fun AppCompatActivity.configActionBar(config: ActionBar.() -> Unit) {
    supportActionBar?.config() ?: Log.wtf(
        "ActionBar",
        "No ActionBar in this Activity! Config skipped.",
        AssertionError()
    )
}

inline var ActionBar.showTitle: Boolean
    get() = displayOptions.hasFlag(ActionBar.DISPLAY_SHOW_TITLE)
    set(value) {
        displayOptions = displayOptions.setFlag(ActionBar.DISPLAY_SHOW_TITLE, value)
    }

inline var ActionBar.showHome: Boolean
    get() = displayOptions.hasFlag(ActionBar.DISPLAY_SHOW_HOME)
    set(value) {
        displayOptions = displayOptions.setFlag(ActionBar.DISPLAY_SHOW_HOME, value)
    }

inline var ActionBar.homeAsUp: Boolean
    get() = displayOptions.hasFlag(ActionBar.DISPLAY_HOME_AS_UP)
    set(value) {
        displayOptions = displayOptions.setFlag(ActionBar.DISPLAY_HOME_AS_UP, value)
    }

inline var ActionBar.useLogo: Boolean
    get() = displayOptions.hasFlag(ActionBar.DISPLAY_USE_LOGO)
    set(value) {
        displayOptions = displayOptions.setFlag(ActionBar.DISPLAY_USE_LOGO, value)
    }

inline var ActionBar.showCustomView: Boolean
    get() = displayOptions.hasFlag(ActionBar.DISPLAY_SHOW_CUSTOM)
    set(value) {
        displayOptions = displayOptions.setFlag(ActionBar.DISPLAY_SHOW_CUSTOM, value)
    }

@PublishedApi
@Suppress("NOTHING_TO_INLINE")
internal inline fun Int.setFlag(flag: Int, value: Boolean): Int {
    return if (value) withFlag(flag)
    else minusFlag(flag)
}
