/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager

object Publishing {
    const val gitUrl = "https://github.com/LouisCAD/Splitties.git"
    const val siteUrl = "https://github.com/LouisCAD/Splitties"
    const val libraryDesc = "A collection of light, general purpose Android libraries in Kotlin."
}

@Suppress("UnstableApiUsage")
fun MavenPublication.setupPom() = pom {
    name.set("Splitties")
    description.set(Publishing.libraryDesc)
    url.set(Publishing.siteUrl)
    licenses {
        license {
            name.set("The Apache Software License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
    developers {
        developer {
            id.set("louiscad")
            name.set("Louis CAD")
            email.set("louis.cognault@gmail.com")
        }
    }
    scm {
        connection.set(Publishing.gitUrl)
        developerConnection.set(Publishing.gitUrl)
        url.set(Publishing.siteUrl)
    }
}

fun PublishingExtension.setupAllPublications(project: Project) {
    project.group = "com.louiscad.splitties"
    project.version = project.thisLibraryVersion
    val mavenPublications = publications.withType<MavenPublication>()
    mavenPublications.all { setupPom() }
    mavenPublications.findByName("kotlinMultiplatform")?.let {
        val prefix = if (project.isFunPack) "splitties-fun-pack" else "splitties"
        val suffix = "-mpp"
        project.afterEvaluate {
            it.artifactId = "$prefix-${project.name}$suffix"
        }
    }
    setupPublishRepo(project)


    project.tasks.register("publishToRepository") {
        group = "publishing"
        val publishTasks = project.tasks.withType<AbstractPublishToMaven>()
        val publishToRepoTasks = publishTasks.filterNot { it.name.endsWith("ToMavenLocal") }

        val hostSpecificPublicationTasks = when (val family = HostManager.host.family) {
            Family.OSX -> publishToRepoTasks.filter {
                it.name.startsWith("publishMacos") ||
                    it.name.startsWith("publishIos") ||
                    it.name.startsWith("publishTvos") ||
                    it.name.startsWith("publishWatchos")
            }
            Family.LINUX -> publishToRepoTasks.filter {
                it.name.startsWith("publishLinux")
            }
            Family.MINGW -> publishToRepoTasks.filter {
                it.name.startsWith("publishMingw")
            }
            else -> error(
                "Unsupported publishing host: ${HostManager.host} from family $family. " +
                    "Kudos for making it run here though."
            )
        }
        hostSpecificPublicationTasks.forEach { task -> dependsOn(task) }

        val publishAll = project.findProperty("mpp.publishAll")?.toString()?.toBoolean() ?: false

        if (publishAll) publishToRepoTasks.filter {
            it.name.startsWith("publishKotlinMultiplatform") ||
                it.name.startsWith("publishMetadata") ||
                it.name.startsWith("publishAndroid") ||
                it.name.startsWith("publishJvm") ||
                it.name.startsWith("publishJs")
        }.forEach { task -> dependsOn(task) }
    }
}

private fun PublishingExtension.setupPublishRepo(project: Project) {
    repositories {
        maven {
            val isDevVersion = project.isDevVersion
            name = "bintray"
            val bintrayUsername = "louiscad"
            val bintrayRepoName = if (isDevVersion) "splitties-dev" else "maven"
            val bintrayPackageName = "splitties"
            setUrl(
                "https://api.bintray.com/maven/" +
                    "$bintrayUsername/$bintrayRepoName/$bintrayPackageName/;" +
                    "publish=0;" + // Never auto-publish to allow override.
                    "override=1"
            )
            credentials {
                username = project.findProperty("bintray_user") as String?
                password = project.findProperty("bintray_api_key") as String?
            }
        }
    }
}

internal val Project.isFunPack: Boolean get() = parent?.name == "fun-packs"
