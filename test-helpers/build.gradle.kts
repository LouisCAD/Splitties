/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android {
    setDefaults()
    namespace = "splitties.internal.test"
}

kotlin {
    androidTarget()
    jvm()
    js { browser(); nodejs() }

    macosX64()
    iosArm64(); iosX64()
    watchosArm32(); watchosArm64()

    linux(x64 = true)
    mingw(x64 = true)

    common {
        dependencies {
            api(Kotlin.test.common)
            api(Kotlin.test.annotationsCommon)
            api(KotlinX.coroutines.core)
        }
        "allJvm" {
            "jvm"()
            "android" {
                dependencies {
                    api(KotlinX.coroutines.android)
                    api(AndroidX.test.ext.junit)
                }
            }
            dependencies {
                api(Kotlin.test.junit)
            }
        }
        "allButJvm" {
            "js"()
            "native" {
                "macosX64"()
                "iosArm64"(); "iosX64"()
                "watchosArm32"(); "watchosArm64"()
                "linuxX64"()
                "mingwX64"()
            }
        }
    }
}
