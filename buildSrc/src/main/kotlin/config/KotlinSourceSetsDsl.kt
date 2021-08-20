/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun KotlinMultiplatformExtension.common(block: SourceSetHierarchyBuilder.() -> Unit) {
    SourceSetHierarchyBuilder(
        sourceSets = sourceSets,
        node = sourceSets.getByName("commonMain")
    ).apply(block)
}

class SourceSetHierarchyBuilder(
    val sourceSets: NamedDomainObjectContainer<KotlinSourceSet>,
    private val node: KotlinSourceSet
) {
    operator fun String.invoke(block: SourceSetHierarchyBuilder.() -> Unit = {}) {
        val sourceSetName = when {
            endsWith("Main") || endsWith("Test") -> this
            else -> "${this@invoke}Main"
        }
        val thisSourceSet: KotlinSourceSet = sourceSets.findByName(sourceSetName)
            ?: sourceSets.create(sourceSetName)
        thisSourceSet.dependsOn(node)
        SourceSetHierarchyBuilder(sourceSets, thisSourceSet).block()
    }

    fun dependencies(configure: KotlinDependencyHandler.() -> Unit) {
        node.dependencies(configure)
    }
}
