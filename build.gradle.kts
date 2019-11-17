/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")


plugins {
    id("de.fayard.refreshVersions")
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

allprojects {
    repositories { setupForProject() }
    tasks.whenTaskAdded {
        if("DebugUnitTest" in name || "ReleaseUnitTest" in name) {
            enabled = false
            // MPP + Android unit testing is so broken we just disable it altogether,
            // (discussion here https://kotlinlang.slack.com/archives/C3PQML5NU/p1572168720226200)
        }
    }
}
