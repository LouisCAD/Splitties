/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    setDefaults()
    namespace = "splitties.fun.pack.android.appcompat"
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splittiesFunPack("android-base"))
            listOf(
                "alertdialog-appcompat",
                "alertdialog-appcompat-coroutines",
                "views-appcompat",
                "views-selectable-appcompat"
            ).forEach { api(splitties(it)) }
        }
    }
}
