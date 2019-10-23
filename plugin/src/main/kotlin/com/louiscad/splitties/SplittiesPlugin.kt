package com.louiscad.splitties

import org.gradle.api.Plugin
import org.gradle.api.Project


open class SplittiesPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Keep in mind the plugin could be applied in multiple modules
        val rootProject = project.rootProject
        if (PluginConfig.configurationDone) return
        rootProject.tasks.register("migrateToAndroidX", MigrateAndroidxTask::class.java)
        PluginConfig.copyPluginsGradleKtsIfNeeded(rootProject)
        PluginConfig.configureSplittiesVersion(rootProject, PluginConfig.SPLITTIES_VERSION)
        PluginConfig.configurationDone = true
    }

}

