/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.kotlin.dsl.withType
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
    project.configurations.create("compileClasspath")
    //TODO: Remove line above when https://youtrack.jetbrains.com/issue/KT-27170 is fixed
    project.group = "com.louiscad.splitties"
    project.version = project.thisLibraryVersion
    val mavenPublications = publications.withType<MavenPublication>()
    mavenPublications.all { setupPom() }
    mavenPublications.findByName("kotlinMultiplatform")?.let {
        val prefix = if (project.isFunPack) "splitties-fun-pack" else "splitties"
        val suffix = "-mpp"
        it.artifactId = "$prefix-${project.name}$suffix"
    }
    setupPublishRepo(project)
    // Mac is the main publishing platform, so we make it publish everything possible
    val publishTasks = project.tasks.withType<AbstractPublishToMaven>()
    publishTasks.matching {
        it.name.startsWith("publishKotlinMultiplatform") ||
        it.name.startsWith("publishMetadata") ||
        it.name.startsWith("publishAndroid") ||
        it.name.startsWith("publishJvm") ||
        it.name.startsWith("publishJs") ||
        it.name.startsWith("publishLinuxX64") ||
        it.name.startsWith("publishMacos") ||
        it.name.startsWith("publishIos")
    }.all {
        onlyIf { HostManager.hostIsMac }
    }
}

private fun PublishingExtension.setupPublishRepo(project: Project) {
    repositories {
        maven {
            name = "bintray"
            val bintrayUsername = "louiscad"
            val bintrayRepoName = if (project.isDevVersion) "splitties-dev" else "maven"
            val bintrayPackageName = "splitties"
            setUrl(
                "https://api.bintray.com/maven/" +
                        "$bintrayUsername/$bintrayRepoName/$bintrayPackageName/;" +
                        "publish=0;" +
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
