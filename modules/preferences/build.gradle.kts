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
    setupNativeSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
            compileOnly(Libs.kotlinX.coroutines.coreCommon)
        }
        androidMain.dependencies {
            api(splitties("appctx"))
            api(splitties("mainthread"))

            api(Libs.kotlin.stdlibJdk7)
            compileOnly(Libs.kotlinX.coroutines.android)
        }
        nativeMain {
            dependencies {
                api(Libs.kotlinX.coroutines.native)
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
