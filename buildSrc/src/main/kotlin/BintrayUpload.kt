
import com.jfrog.bintray.gradle.BintrayExtension
import org.codehaus.groovy.runtime.ProcessGroovyMethods
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.closureOf

/*
 * Copyright (c) 2019. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

fun BintrayExtension.setupPublicationsUpload(
    project: Project,
    publishing: PublishingExtension,
    skipMultiplatformPublication: Boolean = false
) {
    val isDevVersion = ProjectVersions.thisLibrary.contains("-dev-")
    if (!isDevVersion) {
        project.checkNoVersionRanges()
        project.tasks.getByName("bintrayUpload").doFirst {
            val gitTag = ProcessGroovyMethods.getText(
                ProcessGroovyMethods.execute("git describe --dirty")
            ).trim()
            val expectedTag = "v${ProjectVersions.thisLibrary}"
            if (gitTag != expectedTag) error("Expected git tag '$expectedTag' but got '$gitTag'")
        }
    }
    user = project.findProperty("bintray_user") as String?
    key = project.findProperty("bintray_api_key") as String?
    val publicationNames: Array<String> = publishing.publications.filterNot {
        skipMultiplatformPublication && it.name == "kotlinMultiplatform" || it.name == "metadata"
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
    })
}
