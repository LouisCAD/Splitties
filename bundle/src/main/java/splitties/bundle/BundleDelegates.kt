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
@file:Suppress("UNCHECKED_CAST", "unused")

package splitties.bundle

import android.os.Bundle
import splitties.exceptions.illegal
import splitties.uithread.isUiThread
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> BundleSpec.bundle() = BundleDelegate as ReadWriteProperty<BundleSpec, T>
fun <T> BundleSpec.bundleOrNull() = BundleOrNullDelegate as ReadWriteProperty<BundleSpec, T?>

fun <T : Any> BundleSpec.bundleOrDefault(defaultValue: T): ReadWriteProperty<BundleSpec, T> {
    return BundleOrDefaultDelegate(defaultValue)
}

fun <T : Any> BundleSpec.bundleOrElse(defaultValue: () -> T): ReadWriteProperty<BundleSpec, T> {
    return BundleOrElseDelegate(defaultValue)
}

fun <T : Any> BundleSpec.bundle(key: String): ReadWriteProperty<BundleSpec, T> {
    return ExplicitBundleDelegate(key, noNull = true)
}

fun <T> BundleSpec.bundleOrNull(key: String): ReadWriteProperty<BundleSpec, T?> {
    return ExplicitBundleDelegate(key, noNull = false)
}

private val BundleSpec.bundle: Bundle
    get() = (if (isUiThread) currentBundle else bundleByThread.get())
            ?: illegal("Bundle property accessed outside with() function! Thread: $currentThread")

private object BundleDelegate : ReadWriteProperty<BundleSpec, Any> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): Any {
        val key = property.name
        return thisRef.bundle[key].also {
            checkNotNull(it) { "Property $key could not be read" }
        }
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: Any) {
        thisRef.bundle.put(property.name, value)
    }
}

private object BundleOrNullDelegate : ReadWriteProperty<BundleSpec, Any?> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): Any? {
        return thisRef.bundle[property.name]
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: Any?) {
        thisRef.bundle.put(property.name, value)
    }
}

private class BundleOrDefaultDelegate<T : Any>(
        private val defaultValue: T
) : ReadWriteProperty<BundleSpec, T> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        return thisRef.bundle[property.name] as T? ?: defaultValue
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.bundle.put(property.name, value)
    }
}

private class BundleOrElseDelegate<T : Any>(
        private val defaultValue: () -> T
) : ReadWriteProperty<BundleSpec, T> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        return thisRef.bundle[property.name] as T? ?: defaultValue()
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.bundle.put(property.name, value)
    }
}

private class ExplicitBundleDelegate<T>(
        private val key: String,
        private val noNull: Boolean) : ReadWriteProperty<BundleSpec, T> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        return thisRef.bundle[key].also {
            if (noNull) checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.bundle.put(key, value)
    }
}

private inline val currentThread get() = Thread.currentThread()
