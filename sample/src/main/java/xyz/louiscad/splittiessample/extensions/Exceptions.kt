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

package xyz.louiscad.splittiessample.extensions

fun illegal(message: String? = null): Nothing = throw IllegalStateException(message)
fun illegalArg(message: String? = null): Nothing = throw IllegalArgumentException(message)
fun illegalArg(argument: Any?): Nothing = throw IllegalArgumentException("illegal argument: $argument")
fun unsupported(message: String? = null): Nothing = throw UnsupportedOperationException(message)