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
    namespace = "splitties.views.coroutines"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(AndroidX.core.ktx)
            api(KotlinX.coroutines.android)
        }
        getByName("androidInstrumentedTest").dependencies {
            implementation(Kotlin.test.junit)
            implementation(AndroidX.test.coreKtx)
            implementation(AndroidX.test.runner)
            implementation(AndroidX.test.ext.junit)
            implementation(AndroidX.test.espresso.core)
            implementation(KotlinX.coroutines.android)
        }
    }
}
