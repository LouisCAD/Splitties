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
    namespace = "splitties.alertdialog.appcompat.coroutines"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
            api(splitties("alertdialog-appcompat"))
        }
        androidMain.dependencies {
            implementation(KotlinX.coroutines.core)
            implementation(splitties("resources"))
        }
    }
}
