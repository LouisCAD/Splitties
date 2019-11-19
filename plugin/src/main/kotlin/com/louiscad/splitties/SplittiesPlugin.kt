package com.louiscad.splitties

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

open class SplittiesPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.rootProject.run {
        pluginManager.apply("de.fayard.refreshVersions")
        if (alreadyRun.not()) {
            alreadyRun = true
            tasks.register<MigrateAndroidxTask>("migrateToAndroidX")
        }
        Unit
    }

    private companion object {
        private var alreadyRun = false
    }
}
