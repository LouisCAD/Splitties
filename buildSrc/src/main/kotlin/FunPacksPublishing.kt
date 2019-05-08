import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

inline fun KotlinMultiplatformExtension.androidWithFunPackPublication(
    project: Project,
    crossinline configure: KotlinAndroidTarget.() -> Unit = { }
) = android {
    publishLibraryVariants("release")
    mavenPublication { artifactId = "splitties-fun-pack-${project.name}" }; configure()
}
