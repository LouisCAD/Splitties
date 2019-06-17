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
            api(splitties("views"))
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.androidX.cardView)
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
