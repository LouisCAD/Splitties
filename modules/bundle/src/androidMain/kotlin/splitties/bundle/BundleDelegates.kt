/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("UNCHECKED_CAST", "unused")

package splitties.bundle

import android.os.Bundle
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
    get() = currentBundle
        ?: error("Bundle property accessed outside with() function! Thread: $currentThread")

private fun BundleSpec.put(key: String, value: Any?) {
    check(!isReadOnly) {
        "The BundleSpec is in read only mode! If you're trying to mutate extras of " +
                "an Activity, use putExtras instead of withExtras."
    }
    bundle.put(key, value)
}

private object BundleDelegate : ReadWriteProperty<BundleSpec, Any> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): Any {
        val key = property.name
        @Suppress("deprecation") // No good alternative in the public API.
        return checkNotNull(thisRef.bundle[key]) { "Property $key could not be read" }
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: Any) {
        thisRef.put(property.name, value)
    }
}

private object BundleOrNullDelegate : ReadWriteProperty<BundleSpec, Any?> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): Any? {
        @Suppress("deprecation") // No good alternative in the public API.
        return thisRef.bundle[property.name]
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: Any?) {
        thisRef.put(property.name, value)
    }
}

private class BundleOrDefaultDelegate<T : Any>(
    private val defaultValue: T
) : ReadWriteProperty<BundleSpec, T> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        @Suppress("deprecation") // No good alternative in the public API.
        return thisRef.bundle[property.name] as T? ?: defaultValue
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.put(property.name, value)
    }
}

private class BundleOrElseDelegate<T : Any>(
    private val defaultValue: () -> T
) : ReadWriteProperty<BundleSpec, T> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        @Suppress("deprecation") // No good alternative in the public API.
        return thisRef.bundle[property.name] as T? ?: defaultValue()
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.put(property.name, value)
    }
}

private class ExplicitBundleDelegate<T>(
    private val key: String,
    private val noNull: Boolean
) : ReadWriteProperty<BundleSpec, T> {

    override operator fun getValue(thisRef: BundleSpec, property: KProperty<*>): T {
        @Suppress("deprecation") // No good alternative in the public API.
        return thisRef.bundle[key].also {
            if (noNull) checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    override operator fun setValue(thisRef: BundleSpec, property: KProperty<*>, value: T) {
        thisRef.put(key, value)
    }
}

private inline val currentThread get() = Thread.currentThread()
