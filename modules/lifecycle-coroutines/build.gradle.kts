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
            implementation(splitties("mainhandler"))
            implementation(splitties("mainthread"))
            api(Kotlin.stdlib.jdk7)
            api(KotlinX.coroutines.android)
            api(AndroidX.lifecycle.common)
        }
        getByName("androidTest").dependencies {
            implementation(splitties("experimental"))
            implementation(KotlinX.coroutines.test)
            implementation(Kotlin.test.junit)
            implementation(AndroidX.lifecycle.runtime)
        }
        matching { it.name.startsWith("android") }.all {
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
