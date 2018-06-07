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

package splitties.fragmentargs.support

import android.support.v4.app.Fragment
import splitties.exceptions.illegal
import splitties.fragmentargs.putArg
import kotlin.reflect.KProperty

@Suppress("unused") inline fun Fragment.arg() = FragmentArgDelegate
@Suppress("unused") inline fun Fragment.argOrNull() = FragmentArgOrNullDelegate

object FragmentArgDelegate {

    operator fun <T : Any> getValue(thisRef: Fragment, property: KProperty<*>): T {
        val key = property.name
        val args = thisRef.arguments
                ?: illegal("Cannot read property $key if no arguments have been set")
        @Suppress("UNCHECKED_CAST")
        return args.get(key).also {
            checkNotNull(it) { "Property $key could not be read" }
        } as T
    }

    operator fun <T : Any> setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.putArg(property.name, value)
    }
}

object FragmentArgOrNullDelegate {

    operator fun <T> getValue(thisRef: Fragment, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return thisRef.arguments?.get(property.name) as T
    }

    operator fun <T> setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.putArg(property.name, value)
    }
}
