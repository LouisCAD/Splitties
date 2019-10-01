/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import de.fayard.VersionsOnlyMode

plugins {
    id("de.fayard.buildSrcVersions").version("0.6.2")
}

/**
 * Use ./gradlew refreshVersions to find available updates
 * Une ./gradlew buildSrcVersions when you add a new dependency
 */
buildSrcVersions {
    // https://github.com/jmfayard/buildSrcVersions/issues/77
    indent = "    "
    renameLibs = "Deps"
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
