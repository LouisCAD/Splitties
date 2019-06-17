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
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.androidX.coreKtx)
            api(Libs.kotlinX.coroutines.android)
        }
        getByName("androidTest").dependencies {
            implementation(Libs.kotlin.testJunit)
            implementation(Libs.androidX.test.coreKtx)
            implementation(Libs.androidX.test.runner)
            implementation(Libs.androidX.test.ext.junit)
            implementation(Libs.androidX.test.espresso.core)
            implementation(Libs.kotlinX.coroutines.android)
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
