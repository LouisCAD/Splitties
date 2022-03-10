/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import com.android.build.gradle.LibraryExtension as AndroidLibraryExtension

fun AndroidLibraryExtension.setDefaults(generateBuildConfig: Boolean = false) {
    compileSdk = ProjectVersions.androidSdk
    defaultConfig {
        minSdk = 14
        targetSdk = ProjectVersions.androidSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        this.buildConfig = generateBuildConfig
    }
    sourceSets.getByName("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
    }
    sourceSets.getByName("debug") {
        manifest.srcFile("src/androidDebug/AndroidManifest.xml")
        res.srcDir("src/androidDebug/res")
    }
    sourceSets.getByName("release") {
        manifest.srcFile("src/androidRelease/AndroidManifest.xml")
        res.srcDir("src/androidRelease/res")
    }
    sourceSets.getByName("androidTest") {
        java.srcDir("src/commonTest/kotlin")
        java.srcDir("src/androidTest/kotlin")
    }
    packagingOptions {
        // Remove line below when the following issue is ACTUALLY fixed (not just closed)
        // https://github.com/Kotlin/kotlinx.coroutines/issues/1064
        pickFirst("META-INF/kotlinx-coroutines-core.kotlin_module")
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true // Required for Roboelectric
    }
}
