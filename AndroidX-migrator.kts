import java.io.File

println("Let's migrate this Android project from support libraries to AndroidX.")
val dir = File(".")
val androidXClassMappingCsvFile = File(dir, "androidx-class-mapping.csv")
val expectedNumberOfModules = 43
val ignoredRootDirNames = listOf("build", "buildSrc", "gradle", "projectFilesBackup")

val moduleDirectories: List<File> = dir.listFiles { file: File ->
    file.isDirectory &&
            !file.name.startsWith('.') &&
            file.name !in ignoredRootDirNames &&
            file.listFiles { it: File -> it.isDirectory && it.name == "src" }.size == 1
}!!.sortedBy { it.name }
check(moduleDirectories.size == expectedNumberOfModules)

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

val supportLibsToAndroidXMappings = androidXClassMappingCsvFile.readLines().drop(1).map { line ->
    val (supportLibClassName, androidXClassName) = line.split(",").also { check(it.size == 2) }
    check(supportLibClassName.isLegalClassName()) { "Illegal entry in csv: $supportLibClassName" }
    check(androidXClassName.isLegalClassName()) { "Illegal entry in csv: $androidXClassName" }
    supportLibClassName to androidXClassName
}
println("CSV file ok.")

fun String.simpleNameFromFullyQualified(): String = substring(indexOfFirst { it.isUpperCase() })
fun String.packageNameFromClassName(): String = substring(0, indexOfFirst { it.isUpperCase() })
fun String.isLegalClassName() = first().isJavaIdentifierStart() && all {
    it.isJavaIdentifierPart() || it == '.'
}

val supportedExtensions = sourceExtensions + gradleExtension
fun File.migrateToAndroidX() {
    check(extension in supportedExtensions)
    val sourceCode = readText()
    var editedSourceCode = sourceCode
    print("Migrating file named \"$name\" with full name: \"$path\"… ")
    supportLibsToAndroidXMappings.forEach { (supportLibClassName, androidXClassName) ->
        editedSourceCode = editedSourceCode.replace(supportLibClassName, androidXClassName)
    }
    if (editedSourceCode == sourceCode) {
        println("No changes. 🆗")
    } else {
        print("Overwriting file… ")
        writeText(editedSourceCode)
        println("Done. ️✅")
    }
}

println("Starting batch migration")
sourceFiles.forEach {
    it.migrateToAndroidX()
}
gradleFiles.forEach {
    it.migrateToAndroidX()
}

println("AndroidX migration complete!")
println("You now just need to update the dependencies, if not already done.")
