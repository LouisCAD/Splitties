package com.louiscad.splitties

import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File


object PluginConfig {

    const val GROUP = "com.louiscad.splitties"
    const val GRADLE_PROPERTY = "version.$GROUP"
    const val SPLITTIES_VERSION = "3.0.0-alpha06"
    const val PLUGIN_GRADLE_KTS = "plugins.gradle.kts"
    const val SETTINGS_GRADLE_KTS = "settings.gradle.kts"
    const val OK = "✔ \uD83C\uDD97"

    const val INCLUDE_PLUGINS_GRADLE_KTS = """
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}
apply(from = "plugins.gradle.kts")
// rootProject.name = xxx
// include(":app")
"""

    internal var configurationDone = false

    fun configureSplittiesVersion(project: Project, splittiesVersion: String) = with(PluginConfig) {
        project.allprojects {
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

    fun pluginFileContent(): String {
        return this::class.java.getResourceAsStream("/plugins.gradle.kts").reader().readText()
    }

    fun copyPluginsGradleKtsIfNeeded(project: Project) {
        val file = project.rootProject.file(PLUGIN_GRADLE_KTS)
        val settingsFile = project.rootProject.file(SETTINGS_GRADLE_KTS)
        if (settingsFile.canRead() && file.canRead().not()) {
            file.writeText(pluginFileContent())
            println("$OK Created file ./$PLUGIN_GRADLE_KTS")
            throw GradleException("""
                |Action required: Please include $PLUGIN_GRADLE_KTS file in $SETTINGS_GRADLE_KTS
                |    // ./$SETTINGS_GRADLE_KTS
                |    $INCLUDE_PLUGINS_GRADLE_KTS
                | """.trimMargin())
        }

    }
}
