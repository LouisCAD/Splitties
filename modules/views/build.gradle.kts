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
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(splitties("dimensions"))
            api(splitties("resources"))
            api(splitties("systemservices"))
            api(Kotlin.stdlib.jdk7)
            api(AndroidX.annotation)
            api(AndroidX.core.ktx)
            implementation(splitties("mainthread"))
        }
        matching { it.name.startsWith("android") }.all {
            languageSettings.apply {
                enableLanguageFeature("InlineClasses")
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
