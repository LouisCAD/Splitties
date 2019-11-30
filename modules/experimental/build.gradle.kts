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
    ios(supportArm32 = true)
    configure(targets) { configureMavenPublication() }
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(Kotlin.stdlib.common)
        }
        jvmMain.dependencies {
            api(Kotlin.stdlib.jdk7)
        }
        jsMain.dependencies {
            api(Kotlin.stdlib.js)
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
