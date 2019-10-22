package com.louiscad.splitties

import com.louiscad.splitties.AndroidxMigrator.artifact
import com.louiscad.splitties.AndroidxMigrator.gradleSyntax
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.UnknownProjectException
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.tasks.TaskAction
import java.io.File


open class MigrateAndroidxTask: DefaultTask() {
   init {
       group = "help"
       description = "Migrate package to AndroidX"
   }

    @TaskAction
    fun migratePackages() = with(AndroidxMigrator) {
        println()
        println("## Searching Android Support Dependencies")
        println("$OK Parsing file androidx-artifact-mapping.csv")
        val artifacts: List<ArtifactMapping> = readArtifactMappings()
        val androidSupportDependencies = findAndroidSupportDependencies(project, artifacts)
        printGradleSyntax(androidSupportDependencies.map { "${it.group}:${it.name}:${it.version}" })

        println("## Checking that you use compileSdkVersion 28")
        val detected = tryDetectCompileSdkVersion(project.rootDir)
        val version = detected.mapNotNull { it.trim().substringAfter(" ").substringAfter("=").toIntOrNull() }.firstOrNull()
        println(when(version) {
            null -> "⚠️ Make sure you are using compileSdkVersion 28. See $MIGRATE_TO_28"
            28 -> "$OK You are using compileSdkVersion 28"
            else -> throw GradleException("You should migrate first to compileSdkVersion 28. See $MIGRATE_TO_28")
        })

        println()
        println("## Migrating classes from support libraries to AndroidX.")
        println("$OK Parsing file androidx-class-mapping.csv")
        val androidxClassMappings: List<String> = readAndroidxClassMappings()

        val moduleDirectories: List<File> = project.subprojects.map { it.projectDir }
        println("$OK Modules: ${moduleDirectories.map { it.relativeTo(project.rootDir) }}")
        val sourceFiles = sourceFiles(moduleDirectories)
        val gradleFiles = gradleFiles(moduleDirectories)
        println("$OK Found ${sourceFiles.size} source files that may need migration")

        val supportLibsToAndroidXMappings = supportLibsToAndroidXMappings(androidxClassMappings)
        val rawSupportLibsToAndroidXPackageMappings = rawSupportLibsToAndroidXPackageMappings(supportLibsToAndroidXMappings)
        val supportLibsToAndroidXStarImportMappings = supportLibsToAndroidXStarImportMappings(rawSupportLibsToAndroidXPackageMappings)
        println("$OK File androidx-class-mapping.csv parsed correctly")

        val replaces: List<Pair<String, String>> = supportLibsToAndroidXMappings + supportLibsToAndroidXStarImportMappings

        println("$OK Starting batch migration...")
        val editedSourceFilesCount = sourceFiles.count { it.migrateToAndroidX(replaces) }
        val editedGradleFilesCount = gradleFiles.count { it.migrateToAndroidX(replaces) }

        println(
                "\n$OK $editedSourceFilesCount source files (${sourceExtensions.joinToString(",") { it }}) " +
                        "have been migrated (${sourceFiles.count() - editedSourceFilesCount} didn't need it)."
        )
        println(
                "$OK $editedGradleFilesCount gradle files have been migrated " +
                        "(${gradleFiles.count() - editedGradleFilesCount} didn't need it)."
        )

        println()
        println("## Detecting Gradle properties")
        val hasProperties = PROPERTIES.all { project.hasProperty(it) }
        if (hasProperties) {
            println("$OK gradle.properties already contains $PROPERTIES")
        } else {
            val file = project.rootProject.file("gradle.properties")
            file.appendText(GRADLE_PROPERTIES)
            println("$OK Added ${PROPERTIES} to file gradle.properties")
        }

        println()
        println("## Your turn: use instead those Androidx libraries")
        val map = artifacts.associate { it.supportArtifact to it.androidXArtifact }
        printGradleSyntax(androidSupportDependencies.mapNotNull { map[it.artifact] })


    }




}


internal data class ArtifactMapping(
    val supportArtifact: String,
    val androidXArtifact: String
)

internal object AndroidxMigrator {
    const val OK = "✔ \uD83C\uDD97"
    val PROPERTIES = listOf("android.useAndroidX", "android.enableJetifier")
    val GRADLE_PROPERTIES = PROPERTIES.joinToString(separator = "\n", prefix = "\n", postfix = "\n") { "$it=true" }
    val MIGRATE_TO_28 = "https://developer.android.com/about/versions/pie/android-9.0-migration"

    val Dependency.artifact: String
        get() = "$group:$name"

    fun gradleSyntax(artifact: String) : String {
        val configuration = if (artifact.contains("test")) "androidTestImplementation" else "implementation"
        return """$configuration("$artifact")"""
    }

    fun printGradleSyntax(artifacts: List<String>) {
        println(artifacts.joinToString("\n", prefix = "\n", postfix = "\n") { gradleSyntax(it) })
    }

    fun readArtifactMappings(): List<ArtifactMapping> {
        val lines: List<String> = this::class.java.getResourceAsStream("/androidx-artifact-mapping.csv").reader().readLines()
        return lines
            .drop(1)
            .filter { it.contains(",") }
            .map {
                val (old, new) = it.split(",")
                ArtifactMapping(old, new)
            }
    }

    fun tryDetectCompileSdkVersion(rootDir: File): List<String> {
        return rootDir.walk()
            .filter { it.isFile }
            .filter { it.name.endsWith(".gradle.kts") || it.name.endsWith(".gradle") }
            .flatMap { it.readLines().mapNotNull { containsCompileSdkVersion(it) }.asSequence() }
            .toList()
    }

    fun containsCompileSdkVersion(line: String) = when {
        line.contains("compileSdkVersion") -> line
        else -> null
    }

    fun readAndroidxClassMappings(): List<String> {
        return this::class.java.getResourceAsStream("/androidx-class-mapping.csv").reader().readLines()
    }

    fun findAndroidSupportDependencies(project: Project, artifacts: List<ArtifactMapping>): List<Dependency> {
        val supportArtifacts = artifacts.map(ArtifactMapping::supportArtifact)

        val allConfigurations: Set<Configuration> = project.rootProject.allprojects.flatMap {
            it.configurations
        }.toSet()

        return allConfigurations
            .flatMap { configuration ->
                configuration.allDependencies
                    .filter { it.artifact in supportArtifacts }
            }.distinctBy { it.artifact }

    }

    val sourceExtensions = listOf("kt", "java", "xml")

    val gradleExtension = "gradle"

    fun File.findSourceFiles(): List<File> {
        val filesWithExtension = listFiles { file: File ->
            file.extension in sourceExtensions
        }?.asList() ?: emptyList()
        val sourceFiles = listFiles { file: File ->
            file.isDirectory
        }?.flatMap { it.findSourceFiles() } ?: emptyList()
        return filesWithExtension + sourceFiles
    }

    fun sourceFiles(moduleDirectories: List<File>): List<File> = moduleDirectories.map {
        File(it, "src")
    }.flatMap { it.findSourceFiles() }


    fun gradleFiles(moduleDirectories: List<File>): List<File> = moduleDirectories.flatMap {
        it.listFiles { file: File ->
            file.extension == gradleExtension
        }?.asIterable() ?: emptyList()
    }

    fun supportLibsToAndroidXMappings(androidXClassMapping: List<String>): List<Pair<String, String>> {
        return androidXClassMapping.asSequence().drop(1)
                .map { line ->
                    val (supportLibClassName, androidXClassName) = line.split(",").also { check(it.size == 2) }
                    check(supportLibClassName.isLegalClassName()) { "Illegal entry in csv: $supportLibClassName" }
                    check(androidXClassName.isLegalClassName()) { "Illegal entry in csv: $androidXClassName" }
                    supportLibClassName to androidXClassName
                }.sortedByDescending { (supportLibClassName, _) -> supportLibClassName }.toList()
    }
    fun rawSupportLibsToAndroidXPackageMappings(supportLibsToAndroidXMappings: List<Pair<String, String>> ): List<Pair<String, String>> =
            supportLibsToAndroidXMappings.asSequence().map { (supportLibClassName, androidXClassName) ->
                supportLibClassName.packageNameFromClassName() to androidXClassName.packageNameFromClassName()
            }.distinct().toList()

    fun supportLibsToAndroidXStarImportMappings(rawSupportLibsToAndroidXPackageMappings: List<Pair<String, String>>): List<Pair<String, String>> =
            rawSupportLibsToAndroidXPackageMappings.map { (supportLibPackageName, _/*androidXPackageName*/) ->
                val supportLibStarImport = "import $supportLibPackageName.*"
                val androidXStarImports = rawSupportLibsToAndroidXPackageMappings.filter { (slpn, _) ->
                    supportLibPackageName == slpn
                }.joinToString("\n") { (_, axpn) -> "import $axpn.*" }
                supportLibStarImport to androidXStarImports
            }


    fun String.simpleNameFromFullyQualified(): String = substring(indexOfFirst { it.isUpperCase() })

    fun String.packageNameFromClassName(): String = substring(0, indexOfFirst { it.isUpperCase() } - 1)

    fun String.isLegalClassName() = first().isJavaIdentifierStart() && all {
        it.isJavaIdentifierPart() || it == '.'
    }

    val supportedExtensions = sourceExtensions + gradleExtension

    fun File.migrateToAndroidX(replaces: List<Pair<String, String>>): Boolean {
        check(extension in supportedExtensions)
        val sourceCode = readText()
        var editedSourceCode = sourceCode
        val migratingMsg = "Migrating file \"$name\" … "
        print(migratingMsg)
        replaces.forEach { (supportLibSnippet, androidXSnippet) ->
            editedSourceCode = editedSourceCode.replace(supportLibSnippet, androidXSnippet)
        }
        return if (editedSourceCode == sourceCode) {
            print("\b".repeat(migratingMsg.length))
            false
        } else {
            print("overwriting file… ")
            writeText(editedSourceCode)
            println(" Done. ✔🆗") // Emojis can be cut off by terminal line breaks, hence the checkmark.
            true
        }
    }

}
