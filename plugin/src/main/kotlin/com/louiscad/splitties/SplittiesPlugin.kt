package com.louiscad.splitties

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.register

open class SplittiesPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.apply(plugin = "de.fayard.dependencies")
        if (project == project.rootProject) {
            project.apply(plugin = "de.fayard.refreshVersions")
            if (alreadyRun.not()) {
                alreadyRun = true
                project.tasks.register<MigrateAndroidxTask>("migrateToAndroidX")
            }
        }
    }

    private companion object {
        private var alreadyRun = false
    }
}
