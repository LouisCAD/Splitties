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
    jvm()
    js { useCommonJs() }
    macos()
    ios(supportArm32 = true)
    linux(x64 = true)
    mingw(x64 = true)
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(Kotlin.stdlib.common)
            api(Kotlin.stdlib.common)
            api(Kotlin.test.common)
            api(Kotlin.test.annotationsCommon)
            api(KotlinX.coroutines.coreCommon)
        }
        allJvmMain {
            dependencies {
                api(Kotlin.stdlib.jdk7)
                api(KotlinX.coroutines.core)
                api(Kotlin.test.junit)
            }
        }
        androidMain.dependencies {
            api(KotlinX.coroutines.android)
            api(AndroidX.test.ext.junit)
        }
        jsMain.dependencies {
            api(Kotlin.stdlib.js)
            api(KotlinX.coroutines.coreJs)
        }
        nativeMain {
            dependencies {
                api(KotlinX.coroutines.coreNative)
            }
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}
