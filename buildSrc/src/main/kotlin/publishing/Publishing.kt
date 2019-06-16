/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
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
    project.configurations.create("compileClasspath")
    //TODO: Remove line above when https://youtrack.jetbrains.com/issue/KT-27170 is fixed
    project.group = "com.louiscad.splitties"
    project.version = ProjectVersions.thisLibrary
    val publications = publications.withType<MavenPublication>()
    publications.all(Action { setupPom() })
    publications.findByName("kotlinMultiplatform")?.apply {
        artifactId = "splitties-${project.name}-mpp"
    }
}
