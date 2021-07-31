/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.application")
    kotlin("multiplatform")
}

android {
    compileSdkVersion(ProjectVersions.androidSdk)
    defaultConfig {
        applicationId = "com.louiscad.splittiessample"
        minSdkVersion(14)
        targetSdkVersion(ProjectVersions.androidSdk)
        versionCode = 1
        versionName = thisLibraryVersion
        resConfigs("en", "fr")
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
    sourceSets.getByName("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
    }
    packagingOptions {
        exclude("**/*.kotlin_module") // Avoid clashes with common and jvm/android modules
        exclude("**/*.kotlin_builtins") // Reduce apk size
        exclude("**/*.kotlin_metadata") // Reduce apk size
    }
}

kotlin {
    android()
    sourceSets {
        all {
            languageSettings.apply {
                enableLanguageFeature("NewInference")
                useExperimentalAnnotation("kotlin.RequiresOptIn")
                useExperimentalAnnotation("splitties.experimental.ExperimentalSplittiesApi")
            }
        }
        commonMain.dependencies {
            api(Kotlin.stdlib.common)
        }
        androidMain.dependencies {
            implementation(splittiesFunPack("android-material-components-with-views-dsl"))
            arrayOf(
                "arch-lifecycle",
                "arch-room",
                "checkedlazy",
                "exceptions",
                "initprovider",
                "typesaferecyclerview"
            ).forEach { moduleName ->
                implementation(splitties(moduleName))
            }
            arrayOf(
                Kotlin.stdlib.jdk7,
                AndroidX.appCompat,
                AndroidX.core.ktx,
                AndroidX.constraintLayout,
                Google.android.material,
                JakeWharton.timber,
                KotlinX.coroutines.android
            ).forEach {
                implementation(it)
            }
        }
    }
}

dependencies {
    arrayOf(
        "stetho-init",
        "views-dsl-ide-preview"
    ).forEach { moduleName ->
        debugImplementation(splitties(moduleName))
    }
}
