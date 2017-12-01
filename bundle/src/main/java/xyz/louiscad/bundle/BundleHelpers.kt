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

package xyz.louiscad.bundle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlin.reflect.KProperty

inline fun <T : BundleHelper, R> Activity.withExtras(t: T, f: T.() -> R) = intent.extras.with(t, f)
inline fun <T : BundleHelper> Intent.putExtras(t: T, f: T.() -> Unit) {
    replaceExtras(Bundle().apply { with(t, f) })
}
inline fun <T : BundleHelper, R> Bundle.with(t: T, f: T.() -> R) : R {
    //TODO: Add concurrency dependency and check is uiThread
    t.currentBundle = this
    val r = t.f()
    t.currentBundle = null
    return r
}

open class BundleHelper {
    @PublishedApi
    internal var currentBundle: Bundle? = null
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> BundleHelper.bundle(key: String? = null) = BundleDelegate<T>(this, key)

class BundleDelegate<T>(private val helper: BundleHelper, private val key: String? = null) {

    private val bundle get() = helper.currentBundle ?: illegal("Bundle property accessed outside with() function!")

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return bundle.get(key ?: property.name).apply {
            if (this == null && !property.returnType.isMarkedNullable) illegal("Property $key could not be read")
        } as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        bundle.put(key ?: property.name, value)
    }
}
