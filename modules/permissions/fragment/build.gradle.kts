/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    namespace = "splitties.permissions.fragment"
    setDefaults()
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("permissions", "core"))
            api(splitties("fragments"))
            implementation(splitties("appctx"))
            implementation(splitties("intents"))
            implementation(AndroidX.core.ktx)
            implementation(AndroidX.activity.ktx)
        }
    }
}
