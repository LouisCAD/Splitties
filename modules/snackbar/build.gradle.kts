/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

android {
    setDefaults()
}

kotlin {
    android()
    configure(targets) { configureMavenPublication() }
    sourceSets {
        getByName("androidMain").dependencies {
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.androidX.annotation)
            api(Libs.google.material)
            implementation(splitties("resources"))
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
