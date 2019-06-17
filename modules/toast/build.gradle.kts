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
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    android()
    configure(targets) { configureMavenPublication() }
    sourceSets {
        getByName("androidMain").dependencies {
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.androidX.annotation)
            compileOnly(Libs.androidX.fragment)
            implementation(splitties("appctx"))
            implementation(splitties("resources"))
            implementation(splitties("systemservices"))
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
