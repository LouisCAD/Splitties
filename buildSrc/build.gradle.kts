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

val kotlinVersion = "1.3.31" // Don't forget to update in Dependencies.kt too!

// We use a special gradle bintray plugin that supports proper publication for multiplatform.
// Source: https://github.com/vvlevchenko/gradle-bintray-plugin/tree/publications-fix
//TODO: Replace when https://github.com/JetBrains/kotlin-native/issues/2372 is fixed.
val bintrayPluginVersion = "1.8.4-jetbrains-5"
repositories.maven(
    url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies"
).ensureModulesStartingWith("com.jfrog.bintray.gradle:gradle-bintray-plugin")

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:3.5.0-beta04")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:$bintrayPluginVersion")
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
