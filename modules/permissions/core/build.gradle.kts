/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    namespace = "splitties.permissions"
    setDefaults()
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("experimental"))
            api(AndroidX.activity)
            implementation(AndroidX.core.ktx)
            implementation(splitties("appctx"))
            implementation(splitties("lifecycle-coroutines"))
            implementation(splitties("activities"))
            implementation(splitties("intents"))
            implementation(AndroidX.lifecycle.runtime.ktx)
        }
    }
}
