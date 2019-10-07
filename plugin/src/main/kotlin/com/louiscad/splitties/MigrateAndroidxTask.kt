package com.louiscad.splitties

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class MigrateAndroidxTask: DefaultTask() {
   init {
       group = "help"
       description = "Migrate package to AndroidX"
   }

    @TaskAction
    fun migratePackages() = with(AndroidxMigrator) {
        //TODO: use read resource from class loader instead of hard coding it. Could not get ti work.
        val androidXClassMappingCsvFile = File("/Users/jmfayard/GitHub/Splitties/plugin/src/main/resources/androidx-class-mapping.csv")

        val CSVNAME = "androidx-class-mapping.csv"
        println("Let's migrate this Android project from support libraries to AndroidX.")
        val dir = project.rootProject.projectDir

        val moduleDirectories: List<File> = project.subprojects.map { it.projectDir }
        println("moduleDirectories=$moduleDirectories")
        val sourceFiles = sourceFiles(moduleDirectories)
        val gradleFiles = gradleFiles(moduleDirectories)
        println(sourceFiles)
        println("There's ${sourceFiles.size} source files that may need migration")

        val supportLibsToAndroidXMappings = supportLibsToAndroidXMappings(androidXClassMappingCsvFile)
        val rawSupportLibsToAndroidXPackageMappings = rawSupportLibsToAndroidXPackageMappings(supportLibsToAndroidXMappings)
        val supportLibsToAndroidXStarImportMappings = supportLibsToAndroidXMappings(androidXClassMappingCsvFile)
        println("CSV file ok.")

        val replaces: List<Pair<String, String>> = supportLibsToAndroidXMappings + supportLibsToAndroidXStarImportMappings
        val supportedExtensions = sourceExtensions + gradleExtension


        println("Starting batch migration")
        val editedSourceFilesCount = sourceFiles.count { it.migrateToAndroidX(replaces) }
        val editedGradleFilesCount = gradleFiles.count { it.migrateToAndroidX(replaces) }

        println(
                "\n$editedSourceFilesCount source files (${sourceExtensions.joinToString(",") { it }}) " +
                        "have been migrated (${sourceFiles.count() - editedSourceFilesCount} didn't need it)."
        )
        println(
                "$editedGradleFilesCount gradle files have been migrated " +
                        "(${gradleFiles.count() - editedGradleFilesCount} didn't need it)."
        )
        println("AndroidX migration complete!")
        println("You now just need to update the dependencies, if not already done.")

    }
}

internal object AndroidxMigrator {

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

    fun supportLibsToAndroidXMappings(androidXClassMappingCsvFile: File): List<Pair<String, String>> {
        return androidXClassMappingCsvFile.readLines().asSequence().drop(1)
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
        val migratingMsg = "Migrating file named \"$name\" with full name: \"$path\"… "
        print(migratingMsg)
        replaces.forEach { (supportLibSnippet, androidXSnippet) ->
            editedSourceCode = editedSourceCode.replace(supportLibSnippet, androidXSnippet)
        }
        return if (editedSourceCode == sourceCode) {
            print("\b".repeat(migratingMsg.length))
            false
        } else {
            print("Overwriting file… ")
            writeText(editedSourceCode)
            println("Done.✔🆗") // Emojis can be cut off by terminal line breaks, hence the checkmark.
            true
        }
    }

}