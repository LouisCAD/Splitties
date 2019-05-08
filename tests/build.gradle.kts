/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(ProjectVersions.androidSdk)
    defaultConfig {
        applicationId = "com.louiscad.splitties.tests"
        minSdkVersion(14)
        targetSdkVersion(ProjectVersions.androidSdk)
        versionCode = 1
        versionName = ProjectVersions.thisLibrary
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets.all { java.srcDir("src/$name/kotlin") }
    sourceSets.getByName("androidTest") { java.srcDir("src/androidInstrumentedTest/kotlin") }
}

dependencies {
    implementation(splitties("checkedlazy"))
    implementation(splitties("mainthread"))
    implementation(splitties("preferences"))
    androidTestImplementation(Libs.kotlin.testJunit)
    androidTestImplementation(Libs.androidX.test.coreKtx)
    androidTestImplementation(Libs.androidX.test.ext.junit)
    androidTestImplementation(Libs.androidX.test.runner)
    androidTestImplementation(Libs.kotlinX.coroutines.android)
}
