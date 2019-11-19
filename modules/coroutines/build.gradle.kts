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
            api(Kotlin.stdlib.common)
            api(splitties("experimental"))
            implementation(splitties("collections"))
            api(KotlinX.coroutines.coreCommon)
        }
        jvmMain.dependencies {
            api(Kotlin.stdlib.jdk7)
            api(KotlinX.coroutines.core)
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
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}

publishing {
    setupAllPublications(project)
}
