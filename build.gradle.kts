/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import de.fayard.VersionsOnlyMode

plugins {
    id("de.fayard.buildSrcVersions").version("0.5.0")
}

buildSrcVersions {
    versionsOnlyMode = VersionsOnlyMode.KOTLIN_OBJECT
    versionsOnlyFile = "buildSrc/src/main/kotlin/dependencies/Versions.kt"
    indent = "    "
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
