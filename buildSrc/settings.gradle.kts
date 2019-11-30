/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import de.fayard.versions.setupVersionPlaceholdersResolving

@Suppress("UnstableApiUsage")
buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:de.fayard.refreshVersions.gradle.plugin:0.8.6")
}

setupVersionPlaceholdersResolving()
