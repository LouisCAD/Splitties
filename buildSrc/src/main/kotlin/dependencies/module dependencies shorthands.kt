/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun DependencyHandler.splitties(splitName: String) = project(":modules:splitties-$splitName")
fun KotlinDependencyHandler.splitties(splitName: String) = project(":modules:splitties-$splitName")

fun DependencyHandler.splitties(vararg splitNames: String) =
    project(splitNames.splittiesProjectname())

fun KotlinDependencyHandler.splitties(vararg splitNames: String) =
    project(splitNames.splittiesProjectname())

private fun Array<out String>.splittiesProjectname(): String = buildString {
    append(":modules")
    this@splittiesProjectname.runningFold(emptyList<String>()) { list, splitName ->
        (list + splitName).also { newList ->
            append(":splitties")
            newList.forEach {
                append('-')
                append(it)
            }
        }
    }
}

fun DependencyHandler.splittiesFunPack(funPackName: String) = project(":fun-packs:splitties-fun-pack-$funPackName")

fun KotlinDependencyHandler.splittiesFunPack(funPackName: String) = project(":fun-packs:splitties-fun-pack-$funPackName")
