/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Action
import com.android.build.gradle.LibraryExtension as AndroidLibraryExtension

fun AndroidLibraryExtension.setDefaults() {
    compileSdkVersion(ProjectVersions.androidSdk)
    buildToolsVersion(ProjectVersions.androidBuildTools)
    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(ProjectVersions.androidSdk)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets.getByName("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
    }
    sourceSets.getByName("androidTest") {
        java.srcDir("src/commonTest/kotlin")
        java.srcDir("src/androidTest/kotlin")
    }
    // TODO replace with https://issuetracker.google.com/issues/72050365 once released.
    libraryVariants.all(Action {
        generateBuildConfigProvider.configure {
            enabled = false
        }
    })
    packagingOptions {
        // Remove line below when the following issue is ACTUALLY fixed (not just closed)
        // https://github.com/Kotlin/kotlinx.coroutines/issues/1064
        pickFirst("META-INF/kotlinx-coroutines-core.kotlin_module")
    }
}
