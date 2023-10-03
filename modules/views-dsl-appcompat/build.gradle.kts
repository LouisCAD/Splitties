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
    namespace = "splitties.views.dsl.appcompat"
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views-dsl"))
            api(splitties("views-appcompat"))
            implementation(AndroidX.startup.runtime)
            api(AndroidX.annotation)
            api(AndroidX.appCompat)
        }
        all {
            languageSettings.apply {
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("splitties.experimental.InternalSplittiesApi")
            }
        }
    }
}
