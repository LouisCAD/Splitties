/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.androidJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.common
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.js
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.native
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import java.util.Locale

fun KotlinTarget.configureMavenPublication() {
    val suffix = when (platformType) {
        common -> "-metadata"
        jvm -> ""
        js -> "-js"
        androidJvm -> ""
        native -> "-${name.toLowerCase(Locale.ROOT)}"
    }
    mavenPublication { artifactId = "splitties-${project.name}$suffix" }
}
