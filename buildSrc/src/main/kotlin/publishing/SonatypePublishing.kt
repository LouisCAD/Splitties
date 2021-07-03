/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import java.net.URI

fun PublishingExtension.mavenCentralStagingPublishing(
    project: Project,
    sonatypeUsername: String? = project.propertyOrEnvOrNull("sonatype_username"),
    sonatypePassword: String? = project.propertyOrEnvOrNull("sonatype_password"),
    repositoryId: String?
) {
    repositories {
        maven {
            name = "MavenCentralStaging"
            url = when (repositoryId) {
                null -> URI("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                else -> URI("https://oss.sonatype.org/service/local/staging/deployByRepositoryId/$repositoryId/")
            }
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

fun PublishingExtension.sonatypeSnapshotsPublishing(
    project: Project,
    sonatypeUsername: String? = project.propertyOrEnvOrNull("sonatype_username"),
    sonatypePassword: String? = project.propertyOrEnvOrNull("sonatype_password")
) {
    repositories {
        maven {
            name = "SonatypeSnapshots"
            url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}
