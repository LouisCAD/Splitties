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
    namespace = "splitties.intents"
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("bundle"))
            implementation(splitties("bitflags"))
            implementation(splitties("appctx"))
            implementation(splitties("exceptions"))
            compileOnly(AndroidX.fragment)
        }
    }
}
