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
    namespace = "splitties.views.dsl.material"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views-dsl"))
            api(splitties("views-dsl-appcompat"))
            api(splitties("views-dsl-coordinatorlayout"))
            api(splitties("views-dsl-recyclerview"))
            api(splitties("views-material"))
            implementation(AndroidX.startup.runtime)
            api(AndroidX.annotation)
            api(Google.android.material)
        }
        all {
            languageSettings.apply {
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("splitties.experimental.InternalSplittiesApi")
            }
        }
    }
}
