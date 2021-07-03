/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.util.Properties

object ProjectVersions {
    const val androidSdk = 29
}

val Project.thisLibraryVersion: String
    get() {
        val key = "splitties.version"
        return rootProject.extra.properties.getOrPut(key) {
            rootProject.file("libraries_version.txt").readLines().first()
        } as String
    }

val Project.isDevVersion get() = thisLibraryVersion.contains("-dev-")
