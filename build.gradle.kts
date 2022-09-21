/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath(Android.tools.build.gradlePlugin)
    }
}

plugins {
    id("com.osacky.doctor")
    id("com.louiscad.complete-kotlin")
}

doctor {
    disallowCleanTaskDependencies.set(false)
    javaHome {
        //TODO: Remove when https://github.com/runningcode/gradle-doctor/issues/187 is fixed.
        ensureJavaHomeMatches.set(false)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

allprojects {
    afterEvaluate {
        // Remove log pollution until Android support in KMP improves.
        project.extensions.findByType<KotlinMultiplatformExtension>()?.let { kmpExt ->
            kmpExt.sourceSets.removeAll { it.name == "androidAndroidTestRelease" }
        }
    }

    @Suppress("DEPRECATION") // Alternative is to do it for each android plugin id.
    plugins.withType(com.android.build.gradle.BasePlugin::class.java).configureEach {
        project.extensions.getByType<com.android.build.gradle.BaseExtension>().apply {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            lintOptions.isAbortOnError = false
        }
    }
    tasks.withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.whenTaskAdded {
        if ("DebugUnitTest" in name || "ReleaseUnitTest" in name) {
            enabled = false
            // MPP + Android unit testing is so broken we just disable it altogether,
            // (discussion here https://kotlinlang.slack.com/archives/C3PQML5NU/p1572168720226200)
        }
    }
}
