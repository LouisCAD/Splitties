@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Copyright (c) 2016. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(ProjectVersions.androidSdk)
    buildToolsVersion(ProjectVersions.androidBuildTools)
    defaultConfig {
        applicationId = "com.louiscad.splittiessample"
        minSdkVersion(14)
        targetSdkVersion(ProjectVersions.androidSdk)
        versionCode = 1
        versionName = "1.0"
        resConfigs("en", "fr")
        proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        proguardFile("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    sourceSets.forEach { it.java.srcDir("src/${it.name}/kotlin") }
}

dependencies {
    arrayOf(
        ":activities",
        ":alertdialog-appcompat",
        ":appctx",
        ":arch-lifecycle",
        ":arch-room",
        ":bitflags",
        ":bundle",
        ":checkedlazy",
        ":collections",
        ":exceptions",
        ":fragments",
        ":fragmentargs",
        ":initprovider",
        ":intents",
        ":lifecycle-coroutines",
        ":material-colors",
        ":material-lists",
        ":preferences",
        ":systemservices",
        ":toast",
        ":typesaferecyclerview",
        ":mainthread",
        ":views-coroutines",
        ":views-dsl-appcompat",
        ":views-dsl-constraintlayout",
        ":views-dsl-material"
    ).forEach { moduleName ->
        implementation(project(":modules$moduleName"))
    }
    arrayOf(
        ":stetho-init",
        ":views-dsl-ide-preview"
    ).forEach { moduleName ->
        debugImplementation(project(":modules$moduleName"))
    }
    with(Libs) {
        arrayOf(
            kotlin.stdlibJdk7,
            androidX.appCompat,
            androidX.coreKtx,
            androidX.constraintLayout,
            google.material,
            timber,
            kotlinX.coroutines.android
        )
    }.forEach {
        implementation(it)
    }
}

tasks.withType<KotlinCompile>().whenTaskAdded {
    kotlinOptions.freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental")
}

val isRelease: Boolean by extra
if (isRelease) apply { from("../no-version-ranges.gradle.kts") }
