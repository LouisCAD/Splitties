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
    namespace = "splitties.preferences"
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    androidTarget()

    macosX64()
    iosArm64(); iosX64()
    watchosArm32(); watchosArm64()

    configure(targets) { configureMavenPublication() }
    common {
        dependencies {
            api(splitties("experimental"))
            api(KotlinX.coroutines.core)
            implementation(splitties("mainthread"))
        }
        "allButAndroid" {
            "darwin" {
                "macosX64"()
                "iosArm64"(); "iosX64"()
                "watchosArm32"(); "watchosArm64"()
            }
        }
        "android" {
            dependencies {
                api(splitties("appctx"))
                api(AndroidX.dataStore.preferences)
                implementation(KotlinX.coroutines.android)
            }
        }
        "commonTest" {
            dependencies {
                implementation(project(":test-helpers"))
            }
        }
    }
}

dependencies {
    androidTestImplementation(AndroidX.test.runner)
    testImplementation(Testing.robolectric)
}
