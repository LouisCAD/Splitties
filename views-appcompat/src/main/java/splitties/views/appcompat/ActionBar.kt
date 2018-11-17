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

package splitties.views.appcompat

import android.annotation.SuppressLint
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
