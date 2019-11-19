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
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    android()
    macos()
    ios()
    configure(targets) { configureMavenPublication() }
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
            compileOnly(KotlinX.coroutines.coreCommon)
        }
        androidMain.dependencies {
            api(splitties("appctx"))
            api(Kotlin.stdlib.jdk7)
            compileOnly(KotlinX.coroutines.android)
        }
        nativeMain {
            dependencies {
                api(KotlinX.coroutines.coreNative)
            }
        }
        appleMain {
            dependencies {
                implementation(splitties("mainthread"))
            }
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
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
