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
        getByName("androidMain").dependencies {
            api(Libs.kotlin.stdlibJdk7)
            api(splitties("views-selectable"))
            api(splitties("views-selectable-constraintlayout"))
            api(Libs.androidX.constraintLayout)
            implementation(Libs.androidX.appCompat)
            implementation(splitties("views-dsl-appcompat"))
            implementation(splitties("views-dsl-constraintlayout"))
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
