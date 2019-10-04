/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import de.fayard.BuildSrcVersionsExtension

plugins {
    id("de.fayard.buildSrcVersions").version("0.6.4")
    id ("com.gradle.build-scan").version("2.4.2") 
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Libs.kotlinVersion comes from gradle.properties. Keep in sync with buildSrc/build.gradle.kts
Libs.kotlinVersion = findProperty("version.kotlin-stdlib-common") as String


allprojects {
    repositories {
        setupForProject()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

/**
 * Use ./gradlew refreshVersions to find available updates
 * See https://github.com/jmfayard/buildSrcVersions/issues/77
 */
buildSrcVersions {

    // Configuration: https://github.com/jmfayard/buildSrcVersions/issues/53
    rejectVersionIf {
        isStable(currentVersion) && isNonStable(candidate.version)
    }

    val dependenciesWithFullname = Libs.AndroidX.ALL + Libs.AndroidX.Room.ALL +
            Libs.AndroidX.Work.ALL + Libs.AndroidX.Paging.ALL + Libs.AndroidX.Navigation.ALL +
            Libs.AndroidX.Slice.ALL + Libs.AndroidX.ArchCore.ALL + Libs.AndroidX.Test.ALL + Libs.AndroidX.Legacy.ALL

    useFqdnFor(*dependenciesWithFullname
            .map { it.split(":").get(1) }
            .toTypedArray()
    )
}


/** 
For investing build issue and bug reports, run 
  ./gradlew --scan $TASKNAME 
see https://dev.to/jmfayard/the-one-gradle-trick-that-supersedes-all-the-others-5bpg
 **/
buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}


tasks.create<DefaultTask>("hello") {
    group = "custom"
    description = "Hello World! Minimal task useful to debug build problems."
    doLast {
        println("Hello Gradle!")
    }
}