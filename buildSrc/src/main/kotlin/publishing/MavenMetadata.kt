/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import org.gradle.api.UnknownTaskException
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

object Publishing {
    const val gitUrl = "https://github.com/LouisCAD/Splitties.git"
    const val siteUrl = "https://github.com/LouisCAD/Splitties"
    const val libraryDesc = "A collection of light, general purpose Android libraries in Kotlin."
}

fun TaskContainer.emptyJavadocJar(): TaskProvider<Jar> {
    val taskName = "javadocJar"
    return try {
        named(name = taskName)
    } catch (e: UnknownTaskException) {
        register(name = taskName) { archiveClassifier by "javadoc" }
    }
}

@Suppress("UnstableApiUsage")
fun MavenPublication.setupPom(
    gitUrl: String = Publishing.gitUrl,
    siteUrl: String = Publishing.siteUrl,
    libraryDesc: String = Publishing.libraryDesc
) = pom {
    if (name.isPresent.not()) {
        name by artifactId
    }
    description by libraryDesc
    url by siteUrl
    licenses {
        license {
            name by "The Apache Software License, Version 2.0"
            url by "https://www.apache.org/licenses/LICENSE-2.0.txt"
        }
    }
    developers {
        developer {
            id by "louiscad"
            name by "Louis CAD"
            email by "louis.cognault@gmail.com"
        }
    }
    scm {
        connection by gitUrl
        developerConnection by gitUrl
        url by siteUrl
    }
    if (gitUrl.startsWith("https://github.com")) issueManagement {
        system by "GitHub"
        url by gitUrl.replace(".git", "/issues")
    }
}
