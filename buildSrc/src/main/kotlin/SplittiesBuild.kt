import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler

inline fun DependencyHandler.splitties(splitName: String): Dependency {
    return project(mapOf("path" to ":modules:$splitName"))
}
