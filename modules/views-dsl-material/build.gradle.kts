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
    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views-dsl"))
            api(splitties("views-dsl-appcompat"))
            api(splitties("views-dsl-coordinatorlayout"))
            api(splitties("views-dsl-recyclerview"))
            api(splitties("views-material"))
            api(splitties("initprovider"))
            api(Kotlin.stdlib.jdk7)
            api(AndroidX.annotation)
            api(Google.android.material)
        }
        matching { it.name.startsWith("android") }.all {
            languageSettings.apply {
                enableLanguageFeature("InlineClasses")
                useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
                useExperimentalAnnotation("splitties.experimental.InternalSplittiesApi")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
