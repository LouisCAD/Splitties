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

package com.louiscad.splittiessample.extensions

import android.content.Intent
import splitties.init.appCtx
import kotlin.reflect.KClass

inline fun intentOf(kClass: KClass<*>) = Intent(appCtx, kClass.java)

inline fun <reified Component> intentOf(
        configIntent: Intent.() -> Unit = {}
) = Intent(appCtx, Component::class.java).apply(configIntent)
