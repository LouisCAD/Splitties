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
    ios()
    macos()
    js()
    configure(targets) { configureMavenPublication() }
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(kotlin("stdlib-common"))
        }
        androidMain.dependencies {
            api(Libs.kotlin.stdlibJdk7)
        }
        jsMain.dependencies {
            api(kotlin("stdlib-js"))
        }
        commonTest {
            dependencies {
                implementation(project(":test-helpers"))
            }
        }
    }
}

dependencies {
    androidTestImplementation(Libs.androidX.test.runner)
    testImplementation(Libs.roboElectric)
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
