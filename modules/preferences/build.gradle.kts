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
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    android()

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

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
                "iosArm32"(); "iosArm64"(); "iosX64"()
                "watchosArm32"(); "watchosArm64"(); "watchosX86"()
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
    sourceSets {
        all { languageSettings.apply { useExperimentalAnnotation("kotlin.RequiresOptIn") } }
    }
}

dependencies {
    androidTestImplementation(AndroidX.test.runner)
    testImplementation(Testing.robolectric)
}
