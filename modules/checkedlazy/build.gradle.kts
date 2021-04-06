/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
    id("org.jetbrains.dokka")
}

android {
    setDefaults()
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("mainthread"))
            api(splitties("exceptions"))
        }
        commonTest {
            dependencies {
                implementation(project(":test-helpers"))
            }
        }
    }
}

dependencies {
    androidTestImplementation(AndroidX.test.runner)
    testImplementation(Testing.roboElectric)
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
