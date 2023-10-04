@file:Suppress("SpellCheckingInspection")

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
    namespace = "splitties.fun.pack.android.base.with.views.dsl"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splittiesFunPack("android-base"))
            listOf(
                "views-dsl",
                "views-dsl-constraintlayout",
                "views-dsl-recyclerview"
            ).forEach { api(splitties(it)) }
        }
    }
}
