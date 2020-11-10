/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

android {
    setDefaults(project)
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            implementation(splitties("exceptions"))
            implementation(splitties("mainthread"))
            compileOnly(AndroidX.core) // Used for reference in KDOC.
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
