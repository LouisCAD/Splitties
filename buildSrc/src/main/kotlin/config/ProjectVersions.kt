/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.util.Properties

object ProjectVersions {
    const val androidBuildTools = "29.0.2"
    const val androidSdk = 29
}

val Project.thisLibraryVersion: String
    get() {
        val key = "splitties.version"
        return rootProject.extra.properties.getOrElse(key) {
            Properties().also {
                it.load(rootProject.file("libraries_version.properties").reader())
            }.getProperty(key).also { value ->
                rootProject.extra.properties[key] = value // Cache it in root project's extra properties.
            }
        } as String
    }

val Project.isDevVersion get() = thisLibraryVersion.contains("-dev-")
