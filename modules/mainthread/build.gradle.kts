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
    ios(supportArm32 = true)
    macos()
    js { useCommonJs() }
    configure(targets) { configureMavenPublication() }
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(Kotlin.stdlib.common)
        }
        androidMain.dependencies {
            api(Kotlin.stdlib.jdk7)
        }
        jsMain.dependencies {
            api(Kotlin.stdlib.js)
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
