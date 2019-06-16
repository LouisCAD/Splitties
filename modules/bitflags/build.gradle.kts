/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.bintray")
}

kotlin {
    jvm()
    js()
    configure(targets) { configureMavenPublication() }
    sourceSets {
        getByName("commonMain").dependencies {
            api(kotlin("stdlib-common"))
        }
        getByName("jvmMain").dependencies {
            api(kotlin("stdlib-jdk7"))
        }
        getByName("jsMain").dependencies {
            api(kotlin("stdlib-js"))
        }
    }
}

publishing {
    setupAllPublications(project)
}

bintray {
    setupPublicationsUpload(project, publishing)
}
