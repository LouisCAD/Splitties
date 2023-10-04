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
    namespace = "splitties.fun.pack.android.material.components"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splittiesFunPack("android-appcompat"))
            listOf(
                "alertdialog-material",
                "material-lists",
                "snackbar",
                "views-cardview",
                "views-coroutines-material",
                "views-material"
            ).forEach { api(splitties(it)) }
        }
    }
}
