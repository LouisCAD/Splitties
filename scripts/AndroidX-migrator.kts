/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import java.io.File

println("Let's migrate this Android project from support libraries to AndroidX.")
val dir = File(".").parentFile!!
val androidXClassMappingCsvFile = File(dir, "androidx-class-mapping.csv")
val expectedNumberOfModules = 43 // Update this to match the number of modules in the project.
val ignoredRootDirNames =
    listOf("build", "buildSrc", "gradle", "old-dot-gradle", "projectFilesBackup")

val moduleDirectories: List<File> = dir.listFiles { file: File ->
    file.isDirectory &&
            !file.name.startsWith('.') &&
            file.name !in ignoredRootDirNames &&
            file.listFiles { it: File -> it.isDirectory && it.name == "src" }.size == 1
}!!.sortedBy { it.name }
check(moduleDirectories.size == expectedNumberOfModules) {
    "Expected to migrate $expectedNumberOfModules modules but found ${moduleDirectories.size}."
}

val sourceExtensions = listOf("kt", "java", "xml")
val gradleExtension = "gradle"
fun File.findSourceFiles(): List<File> = listFiles { file: File ->
    file.extension in sourceExtensions
}.asList() + listFiles { file: File ->
    file.isDirectory
}.flatMap { it.findSourceFiles() }

val sourceFiles: List<File> = moduleDirectories.map {
    File(it, "src")
}.flatMap { it.findSourceFiles() }
val gradleFiles: List<File> = moduleDirectories.flatMap {
    it.listFiles { file: File ->
        file.extension == gradleExtension
    }.asIterable()
}
println("There's ${sourceFiles.size} source files that may need migration")

val supportLibsToAndroidXMappings = androidXClassMappingCsvFile.readLines().asSequence().drop(1)
    .map { line ->
        val (supportLibClassName, androidXClassName) = line.split(",").also { check(it.size == 2) }
        check(supportLibClassName.isLegalClassName()) { "Illegal entry in csv: $supportLibClassName" }
        check(androidXClassName.isLegalClassName()) { "Illegal entry in csv: $androidXClassName" }
        supportLibClassName to androidXClassName
    }.sortedByDescending { (supportLibClassName, _) -> supportLibClassName }.toList()
val rawSupportLibsToAndroidXPackageMappings: List<Pair<String, String>> =
    supportLibsToAndroidXMappings.asSequence().map { (supportLibClassName, androidXClassName) ->
        supportLibClassName.packageNameFromClassName() to androidXClassName.packageNameFromClassName()
    }.distinct().toList()
val supportLibsToAndroidXStarImportMappings: List<Pair<String, String>> =
    rawSupportLibsToAndroidXPackageMappings.map { (supportLibPackageName, _/*androidXPackageName*/) ->
        val supportLibStarImport = "import $supportLibPackageName.*"
        val androidXStarImports = rawSupportLibsToAndroidXPackageMappings.filter { (slpn, _) ->
            supportLibPackageName == slpn
        }.joinToString("\n") { (_, axpn) -> "import $axpn.*" }
        supportLibStarImport to androidXStarImports
    }
println("CSV file ok.")

val replaces = supportLibsToAndroidXMappings + supportLibsToAndroidXStarImportMappings

fun String.simpleNameFromFullyQualified(): String = substring(indexOfFirst { it.isUpperCase() })
fun String.packageNameFromClassName(): String = substring(0, indexOfFirst { it.isUpperCase() } - 1)
fun String.isLegalClassName() = first().isJavaIdentifierStart() && all {
    it.isJavaIdentifierPart() || it == '.'
}

val supportedExtensions = sourceExtensions + gradleExtension
fun File.migrateToAndroidX(): Boolean {
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

println("Starting batch migration")
val editedSourceFilesCount = sourceFiles.count { it.migrateToAndroidX() }
val editedGradleFilesCount = gradleFiles.count { it.migrateToAndroidX() }

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
