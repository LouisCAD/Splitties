/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    setDefaults()
    namespace = "splitties.stetho.init"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(AndroidX.annotation)
            implementation(AndroidX.startup.runtime)
            api("com.facebook.stetho:stetho:_")
        }
    }
}
