/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import de.fayard.BuildSrcVersionsExtension

plugins {
    /**
     * Plugin versions are set in `gradle.properties` by the property `plugin.$PLUGIN_ID=$PLUGIN_VERSION`.
     * See "settings.gradle.kts"
     */
    id("de.fayard.refreshVersions")
    id ("com.gradle.build-scan")
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Libs.kotlinVersion comes from gradle.properties. Keep in sync with buildSrc/build.gradle.kts
// There is a slight problem with Gradle expecting plugin kotlin-dsl=1.2.9, which is using Kotlin 1.3.50
Libs.kotlinVersion = findProperty("version.org.jetbrains.kotlin") as? String ?: findProperty("version.kotlin-stdlib") as String


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
    orderBy = de.fayard.OrderBy.GROUP_AND_ALPHABETICAL
    useFqdnFor("annotations", *Libs.AndroidX.GROUPS)
}


/** 
For investigating build issue and bug reports, run
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
