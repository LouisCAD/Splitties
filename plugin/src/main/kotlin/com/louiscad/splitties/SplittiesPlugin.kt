package com.louiscad.splitties

import org.gradle.api.Plugin
import org.gradle.api.Project


open class SplittiesPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.run {
        splittiesVersionComesFromGradleProperties()
        Unit
    }

}

fun Project.splittiesVersionComesFromGradleProperties() = with(PluginConfig) {
    val splittiesVersion = findProperty("version.") as? String ?: errorMissingProperty()
    allprojects {
        configurations.all {
            if (name.contains("copy")) return@all
            resolutionStrategy {
                eachDependency {
                    if (requested.group == GROUP)
                        useVersion(splittiesVersion)
                }
            }
        }
    }
}

fun errorMissingProperty(): Nothing = with(PluginConfig) {
    error("""
Splitties version not configured. 
Please add in "gradle.properties" something like:
   $GRADLE_PROPERTY=$SPLITTIES_VERSION
        """.trimIndent()
    )
}