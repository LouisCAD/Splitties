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
        getByName("commonMain").dependencies {
            api(splitties("experimental"))
        }
        getByName("androidMain").dependencies {
            api(splitties("checkedlazy"))
            api(splitties("exceptions"))
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.androidX.annotation)
            api(Libs.androidX.fragment)
            api(Libs.androidX.lifecycle.extensions)
            api(Libs.androidX.lifecycle.viewModel)
            api(Libs.androidX.lifecycle.liveData)
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
