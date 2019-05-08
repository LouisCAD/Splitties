/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("UnstableApiUsage")

import org.gradle.api.artifacts.repositories.MavenArtifactRepository as MAR

fun MAR.ensureGroups(vararg group: String): MAR = apply {
    content { group.forEach { includeGroup(it) } }
}

fun MAR.ensureGroupsByRegexp(vararg regexp: String): MAR = apply {
    content { regexp.forEach { includeGroupByRegex(it) } }
}

fun MAR.ensureGroupsStartingWith(vararg regexp: String): MAR = apply {
    content { regexp.forEach { includeGroupByRegex(it.replace(".", "\\.") + ".*") } }
}

fun MAR.ensureModules(vararg modules: String): MAR = apply {
    content { modules.forEach { includeModule(it.substringBefore(':'), it.substringAfter(':')) } }
}

fun MAR.ensureModulesByRegexp(vararg regexp: String): MAR = apply {
    content {
        regexp.forEach { includeModuleByRegex(it.substringBefore(':'), it.substringAfter(':')) }
    }
}

fun MAR.ensureModulesStartingWith(vararg regexp: String): MAR = apply {
    content {
        regexp.forEach {
            val groupRegex = it.substringBefore(':').replace(".", "\\.")
            val moduleNameRegex = it.substringAfter(':').replace(".", "\\.") + ".*"
            includeModuleByRegex(groupRegex, moduleNameRegex)
        }
    }
}
