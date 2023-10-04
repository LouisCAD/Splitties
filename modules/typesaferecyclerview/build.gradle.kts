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
    namespace = "splitties.typesaferecyclerview"
    sourceSets.getByName("main").java.srcDir("src/androidMain/java")
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views"))
            api(AndroidX.annotation)
            api(AndroidX.recyclerView)
        }
    }
}
