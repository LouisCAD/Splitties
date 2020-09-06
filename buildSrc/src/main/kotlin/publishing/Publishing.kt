/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.withType

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
        project.afterEvaluate {
            it.artifactId = "$prefix-${project.name}"
        }
    }
    setupPublishRepo(project)
}

private fun PublishingExtension.setupPublishRepo(project: Project) {
    repositories {

        fun bintrayProperty(keySuffix: String): String = project.property("splitties.bintray.$keySuffix") as String

        val bintrayUsername = bintrayProperty("user")
        val bintrayApiKey = project.findProperty("bintray_api_key") as String? ?: return@repositories
        maven {
            name = "bintray"
            val isDevVersion = project.isDevVersion
            val bintrayRepoName = bintrayProperty(if (isDevVersion) "repo.dev" else "repo.release")
            val bintrayPackageName = bintrayProperty("package")
            setUrl(
                "https://api.bintray.com/maven/" +
                    "$bintrayUsername/$bintrayRepoName/$bintrayPackageName/;" +
                    "override=1"
                // We don't (no longer) publish on upload because it increases the risk of the bintray API returning
                // HTTP 405 or 409 errors, it allows publishing an invalid release.
                // We publish later once we validated all artifacts.
            )
            credentials {
                username = bintrayUsername
                password = bintrayApiKey
            }
        }
    }
}

internal val Project.isFunPack: Boolean get() = parent?.name == "fun-packs"
