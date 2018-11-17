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

package splitties.fragmentargs

import android.os.Bundle
import android.support.v4.app.Fragment
import splitties.bundle.put
import splitties.exceptions.illegal
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
            ?: illegal("Cannot read property $key if no arguments have been set")
        return args.get(key).also {
            checkNotNull(it) { "Property $key could not be read" }
        }
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
