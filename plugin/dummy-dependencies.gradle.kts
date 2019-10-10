/***
 * This file is only there to make sure that the gradle-versions-plugin
 * finds all dependencies used in this project.
 * It does not contribute in any matter to the output of the plugin.
 * It can be deleted as soon as this bug is resolved
 *
 * Unable to resolve latest versions for Kotlin MPP projects #334
 * https://github.com/ben-manes/gradle-versions-plugin/issues/334
 */

configurations.create("dummy")//.isCanBeResolved = true // doesn't seem to be needed, maybe it's true by default?

fun DependencyHandler.`dummy`(vararg deps: String) =
        deps.forEach { dependencyNotation -> add("dummy", dependencyNotation) }

dependencies {
    dummy("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
    Libs.ALL_RECURSIVE.forEach { dependency -> dummy(dependency) }
}