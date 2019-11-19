/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.artifacts.repositories.MavenArtifactRepository as MvnArtifactRepo

plugins {
    `kotlin-dsl`
    id("de.fayard.refreshVersions")
}

fun MvnArtifactRepo.ensureModulesStartingWith(vararg regexp: String): MvnArtifactRepo = apply {
    @Suppress("UnstableApiUsage")
    content {
        regexp.forEach {
            val groupRegex = it.substringBefore(':').replace(".", "\\.")
            val moduleNameRegex = it.substringAfter(':').replace(".", "\\.") + ".*"
            includeModuleByRegex(groupRegex, moduleNameRegex)
        }
    }
}

repositories {
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

@Suppress("GradlePluginVersion")
dependencies {
    compileOnly(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle:_")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
}

// https://docs.gradle.org/5.6.2/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
