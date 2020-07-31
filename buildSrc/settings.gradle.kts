/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import de.fayard.refreshVersions.bootstrapRefreshVersionsForBuildSrc

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        jcenter()
        maven(url = "https://dl.bintray.com/jmfayard/maven")
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://dl.bintray.com/jmfayard/maven")
    }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.5-dev-008")
}

bootstrapRefreshVersionsForBuildSrc()
