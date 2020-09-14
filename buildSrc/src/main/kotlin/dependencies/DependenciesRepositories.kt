/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.setupForProject() {

    google().ensureGroups(
        "com.google.gms",
        "com.google.firebase",
        "com.google.test.platform",
        "io.fabric.sdk.android",
        "com.crashlytics.sdk.android",
        "org.chromium.net",
        "com.android"
    ).ensureGroupsStartingWith(
        "androidx.",
        "com.android.",
        "com.google.android.",
        "com.google.ar",
        "android.arch"
    )

    mavenCentral().ensureGroupsStartingWith("com.jakewharton.", "com.squareup.")

    jcenter()

    /* // Disabled because we're in Splitties itself.
    maven(
        url = "https://dl.bintray.com/louiscad/splitties-dev"
    ).ensureGroups("com.louiscad.splitties")
    */

    /*
    maven(
        url = "https://dl.bintray.com/kotlin/kotlin-eap"
    ).ensureGroups("org.jetbrains.kotlin")
    */

    maven(
        url = "https://oss.sonatype.org/content/repositories/snapshots"
    ).ensureGroups("org.androidannotations")
}
