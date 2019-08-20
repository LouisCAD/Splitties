/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    jvm()
    js()
    macos()
    ios()
    configure(targets) { configureMavenPublication() }
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(kotlin("stdlib-common"))
            api(splitties("experimental"))
            implementation(splitties("collections"))
            api(Libs.kotlinX.coroutines.coreCommon)
        }
        jvmMain.dependencies {
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.kotlinX.coroutines.core)
        }
        jsMain.dependencies {
            api(kotlin("stdlib-js"))
            api(Libs.kotlinX.coroutines.coreJs)
        }
        nativeMain {
            dependencies {
                api(Libs.kotlinX.coroutines.coreNative)
            }
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}

publishing {
    setupAllPublications(project)
}
