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
    namespace = "splitties.arch.lifecycle"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(splitties("checkedlazy"))
            api(splitties("exceptions"))
            api(AndroidX.annotation)
            api(AndroidX.fragment.ktx)
            api(AndroidX.lifecycle.runtime.ktx)
            api(AndroidX.lifecycle.viewModelKtx)
            api(AndroidX.lifecycle.liveDataKtx)
        }
    }
}
