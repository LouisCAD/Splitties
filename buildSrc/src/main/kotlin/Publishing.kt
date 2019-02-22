
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinCommonCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJsCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJvmCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinOnlyTarget

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

inline fun KotlinMultiplatformExtension.jvmWithPublication(
    project: Project,
    crossinline configure: KotlinOnlyTarget<KotlinJvmCompilation>.() -> Unit = { }
) = jvm {
    mavenPublication { artifactId = "splitties-${project.name}" }; configure()
}

inline fun KotlinMultiplatformExtension.jsWithPublication(
    project: Project,
    crossinline configure: KotlinOnlyTarget<KotlinJsCompilation>.() -> Unit = { }
) = js {
    mavenPublication { artifactId = "splitties-${project.name}-js" }; configure()
}

inline fun KotlinMultiplatformExtension.metadataPublication(
    project: Project,
    crossinline configure: KotlinOnlyTarget<KotlinCommonCompilation>.() -> Unit = { }
) = metadata {
    mavenPublication { artifactId = "splitties-${project.name}-metadata" }; configure()
}

fun Project.enablePublication() {
    group = "com.louiscad.splitties"
    version = ProjectVersions.thisLibrary
}

object Publishing {
    const val gitUrl = "https://github.com/LouisCAD/Splitties.git"
    const val siteUrl = "https://github.com/LouisCAD/Splitties"
    const val libraryDesc = "A collection of light, general purpose Android libraries in Kotlin."
}

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
    val publications = publications.withType<MavenPublication>()
    publications.all(Action { setupPom() })
    publications.findByName("kotlinMultiplatform")?.apply {
        artifactId = "splitties-${project.name}-mpp"
    }
}

fun BintrayExtension.setupPublicationsUpload(project: Project, publishing: PublishingExtension) {
    user = project.findProperty("bintray_user") as String?
    key = project.findProperty("bintray_api_key") as String?
    setPublications(*publishing.publications.map { it.name }.toTypedArray())
    pkg(closureOf<BintrayExtension.PackageConfig> {
        val isDevVersion = ProjectVersions.thisLibrary.contains("-dev-")
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
