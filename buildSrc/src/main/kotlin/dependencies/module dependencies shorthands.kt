/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun DependencyHandler.splitties(splitName: String) = project(":modules:$splitName")
fun KotlinDependencyHandler.splitties(splitName: String) = project(":modules:$splitName")
