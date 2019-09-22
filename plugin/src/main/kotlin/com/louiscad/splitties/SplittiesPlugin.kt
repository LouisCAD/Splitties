package com.louiscad.splitties

import org.gradle.api.Plugin
import org.gradle.api.Project


open class SplittiesPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.run {
        println("Hello World SplittiesPlugin")
        Unit
    }

}
