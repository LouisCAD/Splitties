/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile


buildscript {
    repositories { setupForProject() }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath("com.android.tools.build:gradle:_")
    }
}

plugins {
    id("com.osacky.doctor")
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

allprojects {
    repositories { setupForProject() }

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
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
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
