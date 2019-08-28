/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("SpellCheckingInspection")
plugins {
    id("de.fayard.buildSrcVersions").version("0.4.2")
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



buildSrcVersions {
    indent = "    "

    // TODO implement this https://github.com/jmfayard/buildSrcVersions/issues/55
    // For now I manually deleted Libs.kt and moved Versions.kt in the right folder
    // versionsOnlyMode = VersionsOnlyMode.KOTLIN_OBJECT
    // versionsOnlyFile = "buildSrc/src/main/kotlin/dependencies/VersionX.kt"
}

// Update Gradle with $ ./gradlew buildSrcVersions && ./gradlew wrapper
tasks.withType<Wrapper> {
    gradleVersion = Versions.gradleLatestVersion
    distributionType = Wrapper.DistributionType.ALL
}