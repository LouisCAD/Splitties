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
    namespace = "splitties.mainthread"
}

kotlin {
    androidTarget()
    js { browser(); nodejs() }

    macosX64()
    iosArm64(); iosX64()
    watchosArm32(); watchosArm64()

    configure(targets) { configureMavenPublication() }
    common {
        "darwin" {
            "macosX64"()
            "iosArm64"(); "iosX64"()
            "watchosArm32"(); "watchosArm64"()
        }
        "commonTest" {
            dependencies {
                implementation(project(":test-helpers"))
            }
        }
        "androidUnitTest" {
            dependencies {
                implementation(Kotlin.test)
                implementation(AndroidX.test.runner)
                implementation(Testing.robolectric)
            }
        }
    }
}
