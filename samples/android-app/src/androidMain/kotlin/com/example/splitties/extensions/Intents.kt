/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("NOTHING_TO_INLINE")

package com.example.splitties.extensions

import android.content.Intent
import splitties.init.appCtx
import kotlin.reflect.KClass

inline fun intentOf(kClass: KClass<*>) = Intent(appCtx, kClass.java)

inline fun <reified Component> intentOf(
    configIntent: Intent.() -> Unit = {}
) = Intent(appCtx, Component::class.java).apply(configIntent)
