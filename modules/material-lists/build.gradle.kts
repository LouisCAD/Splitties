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
    namespace = "splitties.material.lists"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views-selectable"))
            api(splitties("views-selectable-constraintlayout"))
            api(AndroidX.constraintLayout)
            implementation(AndroidX.appCompat)
            implementation(splitties("views-dsl-appcompat"))
            implementation(splitties("views-dsl-constraintlayout"))
        }
    }
}
