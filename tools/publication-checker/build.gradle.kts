/*
 * Copyright 2020-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.*
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

repositories {
    mavenCentralStaging(
        project = project,
        repositoryId = System.getenv("sonatype_staging_repo_id")
    ).ensureGroups("com.louiscad.splitties")
}

tasks.matching { it.name.startsWith("lint") }.configureEach { enabled = false }

android {
    setDefaults()
}

fun KotlinTarget.targetsSamePlatformAs(other: KotlinTarget): Boolean = when (this) {
    is KotlinNativeTarget -> other is KotlinNativeTarget && konanTarget == other.konanTarget
    else -> platformType == other.platformType
}

val KotlinTarget.mainSourceSet get() = when {
    platformType == androidJvm && compilations.isEmpty() -> {
        project.the<KotlinMultiplatformExtension>().sourceSets.androidMain
    }
    else -> compilations.getByName("main").defaultSourceSet
}

kotlin {
    androidTarget()
    jvm()
    js { browser(); nodejs() }

    macosX64()
    iosArm64(); iosX64()
    watchosArm32(); watchosArm64()

    mingw(x64 = true)
    linux(x64 = true)

    val group = "com.louiscad.splitties"
    val version = thisLibraryVersion

    sourceSets {
        commonMain {
            dependencies {
                // Needs to be put back if published with HMPP-only,
                // e.g. without this property: kotlin.mpp.enableCompatibilityMetadataVariant=true,
                // Otherwise, the dependencies task fails with an obscure error:
                // "Couldn't resolve metadata artifact for ModuleDependencyIdentifier(groupId=org.jetbrains.kotlinx, moduleId=kotlinx-coroutines-core) in configuration ':tools:publication-checker:iosArm32CompileKlibraries'"
                //TODO: Report the issue before HMPP becomes the default.
                //implementation(KotlinX.coroutines.core)
            }
        }
    }

    rootProject.project(":modules").subprojects {
        if (plugins.hasPlugin("org.jetbrains.kotlin.multiplatform").not()) return@subprojects
        val splitTargets = the<KotlinMultiplatformExtension>().targets.also {
            check(it.isNotEmpty())
        }
        targets.forEach { target ->
            if (target.platformType == common) return@forEach
            val sourceSet: KotlinSourceSet = target.mainSourceSet
            if (splitTargets.any { it.targetsSamePlatformAs(target) }) {
                sourceSet.dependencies {
                    implementation("$group:${project.name}") {
                        version { strictly(version) }
                    }
                }
            }
        }
    }
    rootProject.project(":fun-packs").subprojects {
        if (plugins.hasPlugin("org.jetbrains.kotlin.multiplatform").not()) return@subprojects
        val splitTargets = the<KotlinMultiplatformExtension>().targets.also {
            check(it.isNotEmpty())
        }
        targets.forEach { target ->
            if (target.platformType == common) return@forEach
            val sourceSet: KotlinSourceSet = target.mainSourceSet
            if (splitTargets.any { it.targetsSamePlatformAs(target) }) {
                sourceSet.dependencies {
                    implementation("$group:${project.name}") {
                        version { strictly(version) }
                    }
                }
            }
        }
    }
}
