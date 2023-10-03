/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import com.android.build.gradle.LibraryExtension as AndroidLibraryExtension

fun AndroidLibraryExtension.setDefaults(generateBuildConfig: Boolean = false) {
    compileSdk = ProjectVersions.androidSdk
    defaultConfig {
        minSdk = 14
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        this.buildConfig = generateBuildConfig
    }
    sourceSets.getByName("main") {
        res.srcDir("src/androidMain/res")
    }
    sourceSets.getByName("debug") {
        res.srcDir("src/androidDebug/res")
    }
    sourceSets.getByName("release") {
        res.srcDir("src/androidRelease/res")
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isIncludeAndroidResources = true // Required for Roboelectric
    }
}
