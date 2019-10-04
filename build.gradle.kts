/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")
plugins {
    id("de.fayard.buildSrcVersions").version("0.6.3")
    id ("com.gradle.build-scan").version("2.4.2") 
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

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
        isNonStable(candidate.version) && isNonStable(currentVersion).not()
    }
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