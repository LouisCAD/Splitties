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
        commonMain.dependencies {
            implementation(splitties("coroutines"))
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            implementation(splitties("mainhandler"))
            implementation(splitties("mainthread"))
            api(KotlinX.coroutines.android)
            api(AndroidX.lifecycle.common)
            api(AndroidX.lifecycle.runtime.ktx)
        }
        getByName("androidTest").dependencies {
            implementation(splitties("experimental"))
            implementation(KotlinX.coroutines.test)
            implementation(Kotlin.test.junit)
        }
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
            }
        }
    }
}
