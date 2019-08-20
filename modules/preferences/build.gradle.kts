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
            compileOnly(Libs.kotlinX.coroutines.coreCommon)
        }
        androidMain.dependencies {
            api(splitties("appctx"))

            api(Libs.kotlin.stdlibJdk7)
            compileOnly(Libs.kotlinX.coroutines.android)
        }
        nativeMain {
            dependencies {
                api(Libs.kotlinX.coroutines.coreNative)
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
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
