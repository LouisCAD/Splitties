/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.artifacts.repositories.MavenArtifactRepository as MvnArtifactRepo

plugins {
    `kotlin-dsl`
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

val kotlinVersion = "1.3.50" // Don't forget to update in Dependencies.kt too!

dependencies {
    compileOnly(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle:3.5.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

configurations.all {
    val isKotlinCompiler = name == "embeddedKotlin" ||
            name.startsWith("kotlin") ||
            name.startsWith("kapt")
    if (isKotlinCompiler.not()) resolutionStrategy.eachDependency {
        @Suppress("UnstableApiUsage")
        if (requested.group == "org.jetbrains.kotlin" &&
            requested.module.name == "kotlin-compiler-embeddable"
        ) useVersion(kotlinVersion)
    }
}
