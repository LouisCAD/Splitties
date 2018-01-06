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

@file:Suppress("NOTHING_TO_INLINE")

package splitties.bundle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import splitties.exceptions.illegal
import splitties.uithread.checkUiThread
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <T : BundleHelper, R> Activity.withExtras(t: T, f: T.() -> R) = intent.extras.with(t, f)
inline fun <T : BundleHelper> Intent.putExtras(t: T, f: T.() -> Unit) {
    replaceExtras((extras ?: Bundle()).apply { with(t, f) })
}

inline fun <T : BundleHelper, R> Bundle.with(t: T, f: T.() -> R): R {
    checkUiThread()
    t.currentBundle = this
    val r = t.f()
    t.currentBundle = null
    return r
}

open class BundleHelper {
    @PublishedApi
    internal var currentBundle: Bundle? = null
}

@Suppress("unused") inline fun <T : Any> BundleHelper.bundle() = BundleDelegate
@Suppress("unused") inline fun <T> BundleHelper.bundleOrNull() = BundleOrNullDelegate

inline fun <T : Any> BundleHelper.bundle(key: String): ReadWriteProperty<Any?, T> = ExplicitBundleDelegate<T>(this, key)
inline fun <T> BundleHelper.bundleOrNull(key: String): ReadWriteProperty<Any?, T> = ExplicitBundleDelegate<T>(this, key, noNull = false)

private val BundleHelper.bundle get() = currentBundle ?: illegal("Bundle property accessed outside with() function!")

object BundleDelegate {

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> getValue(thisRef: BundleHelper, property: KProperty<*>): T {
        val key = property.name
        return thisRef.bundle[key].also {
            checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    operator fun <T : Any> setValue(thisRef: BundleHelper, property: KProperty<*>, value: T) {
        thisRef.bundle.put(property.name, value)
    }
}

object BundleOrNullDelegate {

    @Suppress("UNCHECKED_CAST")
    operator fun <T> getValue(thisRef: BundleHelper, property: KProperty<*>): T {
        return thisRef.bundle[property.name] as T
    }

    operator fun <T> setValue(thisRef: BundleHelper, property: KProperty<*>, value: T) {
        thisRef.bundle.put(property.name, value)
    }
}

@PublishedApi
internal class ExplicitBundleDelegate<T>(private val helper: BundleHelper,
                                         private val key: String,
                                         private val noNull: Boolean = true) : ReadWriteProperty<Any?, T> {

    private val bundle get() = helper.currentBundle ?: illegal("Bundle property accessed outside with() function!")

    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return bundle[key].also {
            if (noNull) checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        bundle.put(key, value)
    }
}
