/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("UnusedImport") // Needed for delegates imports to not be removed by the IDE.

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.jfrog.bintray.gradle.BintrayExtension
import org.codehaus.groovy.runtime.ProcessGroovyMethods
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.existing
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun BintrayExtension.setupPublicationsUpload(
    project: Project,
    publishing: PublishingExtension,
    skipMetadataPublication: Boolean = false,
    skipMultiplatformPublication: Boolean = skipMetadataPublication
) {
    val bintrayUpload: TaskProvider<Task> by project.tasks.existing
    val publishToMavenLocal: TaskProvider<Task> by project.tasks.existing
    bintrayUpload.dependsOn(publishToMavenLocal)
    if (!isDevVersion) {
        project.checkNoVersionRanges()
        bintrayUpload.configure {
            doFirst {
                val gitTag = ProcessGroovyMethods.getText(
                    Runtime.getRuntime().exec("git describe --dirty")
                ).trim()
                val expectedTag = "v${ProjectVersions.thisLibrary}"
                if (gitTag != expectedTag) error("Expected git tag '$expectedTag' but got '$gitTag'")
            }
        }
    }
    user = project.findProperty("bintray_user") as String?
    key = project.findProperty("bintray_api_key") as String?
    val publicationNames: Array<String> = publishing.publications.filterNot {
        skipMetadataPublication && it.name == "metadata" ||
                skipMultiplatformPublication && it.name == "kotlinMultiplatform"
    }.map { it.name }.toTypedArray()
    setPublications(*publicationNames)
    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = if (isDevVersion) "splitties-dev" else "maven"
        name = "splitties"
        desc = Publishing.libraryDesc
        websiteUrl = Publishing.siteUrl
        issueTrackerUrl = "https://github.com/LouisCAD/Splitties/issues"
        vcsUrl = Publishing.gitUrl
        setLicenses("Apache-2.0")
        publicDownloadNumbers = true
        githubRepo = "LouisCAD/Splitties"
        publish = isDevVersion
    })
}
