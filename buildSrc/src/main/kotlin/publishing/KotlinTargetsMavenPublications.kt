/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.androidJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.common
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.js
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.native
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import java.util.Locale

fun KotlinTarget.configureMavenPublication(
    publishReleaseVariantOnly: Boolean = false
) {
    require(publishReleaseVariantOnly.not()) {
        "Consuming release libraries from Android app modules doesn't work despite matchingFallbacks, " +
            "so we require to publish both debug and release variants for now."
    }
    val isInMultiplatformModule = project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")
    if (isInMultiplatformModule.not()) return // The mavenPublication block would not be run anyway.
    val suffix = when (platformType) {
        common -> "-metadata"
        jvm -> "-jvm"
        js -> "-js"
        androidJvm -> "-android"
        native -> "-${name.toLowerCase(Locale.ROOT)}"
    }
    mavenPublication {
        val prefix = if (project.isFunPack) "splitties-fun-pack" else "splitties"
        project.afterEvaluate {
            artifactId = "$prefix-${project.name}$suffix"
        }
        if (publishReleaseVariantOnly && platformType == androidJvm) {
            // We disable metadata generation for Android publications, so the release variants can
            // be used for any buildType of the consumer projects without having to specify
            // matchingFallbacks unless the multiplatform artifact is used...
            // but this doesn't seem to work with application modules, so it's guarded by publishReleaseVariantOnly.
            val capitalizedPublicationName = "${name.first().toTitleCase()}${name.substring(1)}"
            val metadataTaskName = "generateMetadataFileFor${capitalizedPublicationName}Publication"
            project.tasks.named(metadataTaskName) { enabled = false }
        }
    }
    if (platformType == androidJvm) {
        this as KotlinAndroidTarget
        publishLibraryVariantsGroupedByFlavor = true
        if (publishReleaseVariantOnly) {
            publishLibraryVariants("release")
            // Relies on metadata to be disabled (done above) to avoid buildType mismatch on consumers.
        } else {
            publishAllLibraryVariants()
        }
    }
}
