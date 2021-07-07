/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import org.gradle.api.artifacts.dsl.RepositoryHandler

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

    mavenCentral()
}
