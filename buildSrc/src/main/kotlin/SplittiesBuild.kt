import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun DependencyHandler.splitties(splitName: String): Dependency {
    return project(mapOf("path" to ":modules:$splitName"))
}

fun KotlinDependencyHandler.splitties(splitName: String): Dependency {
    return project(mapOf("path" to ":modules:$splitName"))
}
