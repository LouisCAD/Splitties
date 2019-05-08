/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("UNCHECKED_CAST", "unused")

package splitties.fragmentargs

import android.os.Bundle
import androidx.fragment.app.Fragment
import splitties.bundle.put
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> Fragment.arg() = FragmentArgDelegate as ReadWriteProperty<Fragment, T>
fun <T> Fragment.argOrNull() = FragmentArgOrNullDelegate as ReadWriteProperty<Fragment, T?>

fun <T : Any> Fragment.argOrDefault(defaultValue: T): ReadWriteProperty<Fragment, T> {
    return FragmentArgOrDefaultDelegate(defaultValue)
}

fun <T : Any> Fragment.argOrElse(defaultValue: () -> T): ReadWriteProperty<Fragment, T> {
    return FragmentArgOrElseDelegate(defaultValue)
}

private object FragmentArgDelegate : ReadWriteProperty<Fragment, Any> {

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): Any {
        val key = property.name
        val args = thisRef.arguments
            ?: error("Cannot read property $key if no arguments have been set")
        return checkNotNull(args.get(key)) { "Property $key could not be read" }
    }

    override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: Any) {
        thisRef.putArg(property.name, value)
    }
}

private object FragmentArgOrNullDelegate : ReadWriteProperty<Fragment, Any?> {

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): Any? {
        return thisRef.arguments?.get(property.name)
    }

    override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: Any?) {
        thisRef.putArg(property.name, value)
    }
}

private class FragmentArgOrDefaultDelegate<T : Any>(
    private val defaultValue: T
) : ReadWriteProperty<Fragment, T> {

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return thisRef.arguments?.get(property.name) as T? ?: defaultValue
    }

    override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.putArg(property.name, value)
    }
}

private class FragmentArgOrElseDelegate<T : Any>(
    private val defaultValue: () -> T
) : ReadWriteProperty<Fragment, T> {

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return thisRef.arguments?.get(property.name) as T? ?: defaultValue()
    }

    override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.putArg(property.name, value)
    }
}

internal fun Fragment.putArg(key: String, value: Any?) {
    val args = arguments ?: Bundle().also { arguments = it }
    args.put(key, value)
}
