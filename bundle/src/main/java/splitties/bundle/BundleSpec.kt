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
import splitties.uithread.isUiThread
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <Spec : BundleSpec, R> Activity.withExtras(spec: Spec, block: Spec.() -> R): R {
    return intent.extras.with(spec, block)
}

inline fun <Spec : BundleSpec> Intent.putExtras(spec: Spec, block: Spec.() -> Unit) {
    replaceExtras((extras ?: Bundle()).apply { with(spec, block) })
}

inline fun <Spec : BundleSpec, R> Bundle.with(spec: Spec, block: Spec.() -> R): R = try {
    this.putIn(spec)
    spec.block()
} finally {
    removeBundleFrom(spec)
}

@PublishedApi internal fun Bundle.putIn(spec: BundleSpec) {
    if (isUiThread) spec.currentBundle = this else spec.bundleByThread.set(this)
}

@PublishedApi internal fun removeBundleFrom(spec: BundleSpec) {
    if (isUiThread) spec.currentBundle = null else spec.bundleByThread.remove()
}

open class BundleSpec {
    internal val bundleByThread by lazy { ThreadLocal<Bundle?>() }
    internal var currentBundle: Bundle? = null
}

@Suppress("unused") inline fun BundleSpec.bundle() = BundleDelegate
@Suppress("unused") inline fun BundleSpec.bundleOrNull() = BundleOrNullDelegate

inline fun <T : Any> BundleSpec.bundle(key: String): ReadWriteProperty<Any?, T> {
    return ExplicitBundleDelegate(this, key, noNull = true)
}

inline fun <T> BundleSpec.bundleOrNull(key: String): ReadWriteProperty<Any?, T> {
    return ExplicitBundleDelegate(this, key, noNull = false)
}

private val BundleSpec.bundle: Bundle
    get() = (if (isUiThread) currentBundle else bundleByThread.get())
            ?: illegal("Bundle property accessed outside with() function! Thread: $currentThread")

object BundleDelegate {

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        val key = property.name
        return thisRef.bundle[key].also {
            checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    operator fun <T : Any> setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.bundle.put(property.name, value)
    }
}

object BundleOrNullDelegate {

    @Suppress("UNCHECKED_CAST")
    operator fun <T> getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        return thisRef.bundle[property.name] as T
    }

    operator fun <T> setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.bundle.put(property.name, value)
    }
}

@PublishedApi
internal class ExplicitBundleDelegate<T>(private val spec: BundleSpec,
                                         private val key: String,
                                         private val noNull: Boolean) : ReadWriteProperty<Any?, T> {

    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return spec.bundle[key].also {
            if (noNull) checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        spec.bundle.put(key, value)
    }
}

private inline val currentThread get() = Thread.currentThread()

@Deprecated("Name changed.", ReplaceWith("BundleSpec", "splitties.bundle.BundleSpec"))
typealias BundleHelper = BundleSpec
