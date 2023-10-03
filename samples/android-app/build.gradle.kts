/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = ProjectVersions.androidSdk
    namespace = "com.example.splitties"
    defaultConfig {
        applicationId = "com.louiscad.splittiessample"
        minSdk = 21
        targetSdk = ProjectVersions.androidSdk
        versionCode = 1
        versionName = thisLibraryVersion
        resourceConfigurations += setOf("en", "fr")
        proguardFile("../proguard-android-really-optimize.txt")
        proguardFile("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        val debugKeystoreFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
        if (debugKeystoreFile.exists()) getByName("debug") {
            storeFile = debugKeystoreFile
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storePassword = "android"
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.findByName("debug")
        }
    }
    packagingOptions.resources {
        excludes += "**/*.kotlin_module" // Avoid clashes with common and jvm/android modules
        excludes += "**/*.kotlin_builtins" // Reduce apk size
        excludes += "**/*.kotlin_metadata" // Reduce apk size
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=splitties.experimental.ExperimentalSplittiesApi"
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    debugImplementation(splitties("stetho-init"))

    implementation(splittiesFunPack("android-material-components-with-views-dsl"))

    implementation(splitties("arch-lifecycle"))
    implementation(splitties("arch-room"))
    implementation(splitties("checkedlazy"))
    implementation(splitties("exceptions"))
    implementation(splitties("typesaferecyclerview"))

    implementation(Kotlin.stdlib.jdk7)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.constraintLayout)
    implementation(Google.android.material)
    implementation(JakeWharton.timber)
    implementation(KotlinX.coroutines.android)
}
