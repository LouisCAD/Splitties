/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.bintray")
}

android {
    setDefaults()
}

kotlin {
    metadataPublication(project)
    androidWithPublication(project)
    sourceSets {
        getByName("commonMain").dependencies {
            api(splitties("experimental"))
        }
        getByName("androidMain").dependencies {
            implementation(splitties("mainhandler"))
            implementation(splitties("mainthread"))
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.kotlinX.coroutines.android)
            api(Libs.androidX.lifecycle.common)
        }
        getByName("androidTest").dependencies {
            implementation(splitties("experimental"))
            implementation(Libs.kotlinX.coroutines.test)
            implementation(Libs.kotlin.testJunit)
            implementation(Libs.androidX.lifecycle.runtime)
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

    bintray {
        setupPublicationsUpload(project, publishing, skipMetadataPublication = true)
    }
}
