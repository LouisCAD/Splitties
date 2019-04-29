/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.setupForProject() {
    jcenter()

    mavenCentral().ensureGroupsStartingWith("com.jakewharton.", "com.squareup.")

    google().ensureGroups(
        "com.google.gms",
        "com.google.firebase",
        "io.fabric.sdk.android",
        "com.crashlytics.sdk.android",
        "org.chromium.net"
    ).ensureGroupsStartingWith(
        "androidx.",
        "com.android.",
        "com.google.android.",
        "com.google.ar",
        "android.arch"
    )

    maven(url = "https://maven.fabric.io/public").ensureGroups("io.fabric.tools")

    maven(
        url = "https://dl.bintray.com/louiscad/splitties-dev"
    ).ensureGroups("com.louiscad.splitties")

    if ("eap" in Versions.kotlin) maven(
        url = "https://dl.bintray.com/kotlin/kotlin-eap"
    ).ensureGroups("org.jetbrains.kotlin")

    maven(
        url = "https://kotlin.bintray.com/kotlinx"
    ).ensureModulesByRegexp("org.jetbrains.kotlinx:kotlinx-serialization\\-.*")

    maven(
        url = "https://oss.sonatype.org/content/repositories/snapshots"
    ).ensureGroups("org.androidannotations")
}
