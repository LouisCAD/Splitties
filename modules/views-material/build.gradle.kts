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
    namespace = "splitties.views.material"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views"))
            api(splitties("views-appcompat"))
            api(splitties("views-cardview"))
            api(splitties("views-recyclerview"))
            api(splitties("snackbar"))
            api(AndroidX.annotation)
            api(Google.android.material)
        }
    }
}
