/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project

object ProjectVersions {
    const val androidBuildTools = "28.0.3"
    const val androidSdk = 28
}

val Project.thisLibraryVersion: String get() = property("splitties.version") as String
val Project.isDevVersion get() = thisLibraryVersion.contains("-dev-")
