package com.louiscad.splitties

import org.gradle.api.Plugin
import org.gradle.api.Project


open class SplittiesPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.rootProject.run {
        if (PluginConfig.ALREADY_RUN.not()) {
            PluginConfig.ALREADY_RUN = true
            tasks.register("migrateToAndroidX", MigrateAndroidxTask::class.java)
            splittiesVersionComesFromGradleProperties()
        }
        Unit
    }

}

fun Project.splittiesVersionComesFromGradleProperties() = with(PluginConfig) {
    val splittiesVersion = findProperty(GRADLE_PROPERTY) as? String ?: SPLITTIES_VERSION
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
