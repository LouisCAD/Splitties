/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    setDefaults(generateBuildConfig = true)
    namespace = "splitties.views.dsl.core"
}

kotlin {
    androidTarget()
    configure(targets) { configureMavenPublication(publishReleaseVariantOnly = false) }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(splitties("views"))
            api(AndroidX.annotation)
            implementation(splitties("collections"))
            implementation(splitties("exceptions"))
            implementation(splitties("appctx"))
        }
        all {
            languageSettings.apply {
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("splitties.experimental.InternalSplittiesApi")
            }
        }
    }
}
