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
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views-dsl"))
            api(AndroidX.annotation)
            api(AndroidX.constraintLayout)
            implementation(splitties("collections"))
        }
        all {
            languageSettings.apply {
                enableLanguageFeature("InlineClasses")
                optIn("kotlin.contracts.ExperimentalContracts")
            }
        }
    }
}
