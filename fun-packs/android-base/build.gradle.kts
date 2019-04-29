@file:Suppress("SpellCheckingInspection")

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
    androidWithFunPackPublication(project)
    sourceSets {
        getByName("androidMain").dependencies {
            listOf(
                "activities",
                "appctx",
                "bitflags",
                "bundle",
                "collections",
                "dimensions",
                "fragments",
                "fragmentargs",
                "intents",
                "lifecycle-coroutines",
                "mainhandler",
                "mainthread",
                "material-colors",
                "permissions",
                "preferences",
                "resources",
                "systemservices",
                "toast",
                "views",
                "views-coroutines",
                "views-recyclerview",
                "views-selectable",
                "views-selectable-constraintlayout"
            ).forEach { api(splitties(it)) }
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
