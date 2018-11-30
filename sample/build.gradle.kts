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
    kotlin("android.extensions")
}

android {
    val projectSdk_version: Int by extra
    val projectBuildTools_version: String by extra
    compileSdkVersion(projectSdk_version)
    buildToolsVersion(projectBuildTools_version)
    defaultConfig {
        applicationId = "com.louiscad.splittiessample"
        minSdkVersion(14)
        targetSdkVersion(projectSdk_version)
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
    sourceSets {
        getByName("main") {
            this.java.srcDir("src/main/kotlin")
        }
    }
}

dependencies {
    // This project"s libraries
    implementation(project(":activities"))
    implementation(project(":alertdialog-appcompat"))
    implementation(project(":appctx"))
    implementation(project(":arch-lifecycle"))
    implementation(project(":arch-room"))
    implementation(project(":bitflags"))
    implementation(project(":bundle"))
    implementation(project(":checkedlazy"))
    implementation(project(":collections"))
    implementation(project(":exceptions"))
    implementation(project(":fragments"))
    implementation(project(":fragmentargs"))
    implementation(project(":initprovider"))
    implementation(project(":intents"))
    implementation(project(":material-colors"))
    implementation(project(":material-lists"))
    implementation(project(":preferences"))
    debugImplementation(project(":stetho-init"))
    implementation(project(":systemservices"))
    implementation(project(":toast"))
    implementation(project(":typesaferecyclerview"))
    implementation(project(":mainthread"))
    implementation(project(":views-dsl-appcompat"))
    implementation(project(":views-dsl-constraintlayout"))
    implementation(project(":views-dsl-material"))
    debugImplementation(project(":views-dsl-ide-preview"))

    implementation(Libs.kotlin.stdlibJdk7)
    implementation(Libs.androidX.appCompat)
    implementation(Libs.androidX.coreKtx)
    implementation(Libs.androidX.constraintLayout)
    implementation(Libs.google.material)
    implementation(Libs.timber)
    implementation(Libs.kotlinX.coroutines.android)
}

tasks.withType<KotlinCompile>().forEach {
    it.kotlinOptions {
        freeCompilerArgs += "-Xuse-experimental=splitties.experimental.ExperimentalSplittiesApi"
    }
}

val isRelease: Boolean by extra
if (isRelease) apply { from("../no-version-ranges.gradle.kts") }
