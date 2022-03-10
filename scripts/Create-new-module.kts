/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

// To make the IDE recognize this file, copy-paste it in a Scratch File (cmd/ctrl + shift + N).

println("Welcome in Create new module by Louis CAD")

inline fun <R> runOrRetry(block: () -> R): R {
    while (true) try {
        return block()
    } catch (t: Exception) {
        println(t.message ?: t)
    }
}

val currentDir = File(".")
val settingsFile = currentDir.resolve("settings.gradle.kts")
check(settingsFile.exists()) { "No ${settingsFile.name} found!" }

////////////////////////////////////////////////////////////////////////////////////////////////////
/// Ask selection of a Gradle template. ////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

val templatesDirectory = currentDir.resolve("scripts").resolve("gradle_templates")
check(templatesDirectory.exists()) { "Template directory (${templatesDirectory.name}) not found!" }

val templateDirNames: List<String> = checkNotNull(templatesDirectory.list { file, name ->
    file.isDirectory && name.startsWith('.').not()
}).asList().also {
    check(it.isNotEmpty()) { "No templates found!" }
}

println("Please select a template")

templateDirNames.forEachIndexed { i, fileName ->
    println("${i + 1}. $fileName")
}

println("Enter the number: ")

val templateIndex = runOrRetry {
    val input = readLine() ?: ""
    val number = input.toIntOrNull() ?: throw IllegalStateException("Please, input a number.")
    number.also {
        require(it in 1..templateDirNames.size) { "$it is not among the available options." }
    } - 1
}
val selectedTemplateDirName = templateDirNames[templateIndex]
val selectedTemplateDir = templatesDirectory.resolve(selectedTemplateDirName)

println("You selected $selectedTemplateDirName\n")

////////////////////////////////////////////////////////////////////////////////////////////////////
/// Ask module name and relative path. /////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

val moduleName = runOrRetry {
    println("Please type the name of the module you want to create")
    println("Enter the name: ")
    (readLine() ?: "").also {
        require(it.isNotBlank()) { "Name entered is blank" }
        //TODO: Check name is valid
        val file = currentDir.resolve(it)
        check(file.exists().not()) {
            "A ${if (file.isDirectory) "directory" else "file"} already has this name!"
        }
    }
}
print("Please type the relative path of the new module (target directory, ")
println("name of module excluded), or leave empty to use this directory as root.")

val relativePathName = (readLine() ?: "").let {
    if (it.endsWith('/') || it.isEmpty()) it else "$it/"
}

val destinationDir: File = currentDir.resolve("$relativePathName$moduleName")

////////////////////////////////////////////////////////////////////////////////////////////////////
/// Copy template into destination directory, asking any needed value with a € symbol (instead of $) /
// string template (e.g. €{some key}). /////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

fun Path.copyTo(dest: Path) {
    val src = this
    Files.walk(src).forEach { s ->
        val d = dest.resolve(src.relativize(s))
        when {
            Files.isSymbolicLink(s) -> {
                Files.createSymbolicLink(
                    d,
                    Files.readSymbolicLink(s).also {
                        check(it.isAbsolute.not()) {
                            "Only relative symbolic links are supported"
                        }
                    }
                )
                return@forEach
            }
            Files.isDirectory(s) -> {
                if (!Files.exists(d)) Files.createDirectory(d)
                return@forEach
            }
            else -> Files.copy(s, d)
        }
    }
}

val destinationPath: Path = destinationDir.toPath()
val selectedTemplatePath: Path = selectedTemplateDir.toPath()

selectedTemplatePath.copyTo(destinationPath)

/**
 * Runs the [action] on this file, then, if the result of the lambda (if not null) or this file,
 * is a directory, calls this function on all the files inside.
 * If the directory is moved or renamed in [action], you need to return it in the lambda, or failure
 * will happen because the referenced file no longer exists.
 */
fun File.doRecursively(action: (File) -> File?) {
    val newOrCurrentFile = action(this) ?: this
    if (newOrCurrentFile.isDirectory) {
        checkNotNull(newOrCurrentFile.listFiles()).forEach { it.doRecursively(action) }
    }
}

class LazyMap<K, V>(
    private val backingMap: MutableMap<K, V> = mutableMapOf(),
    private val createEntry: (K) -> V
) : Map<K, V> by backingMap {
    override fun get(key: K): V {
        if (key !in backingMap) backingMap[key] = createEntry(key)
        return backingMap[key]!!
    }
}

val promptingMap = LazyMap<String, String>(mutableMapOf("module name" to moduleName)) { key ->
    runOrRetry {
        println("$key required. Please, enter it:")
        (readLine() ?: "").also {
            require(it.isNotBlank()) { "Blank fields are not supported yet." }
        }
    }
}

destinationDir.doRecursively { file ->
    val templatePrefix = "!template!"
    if (file.isDirectory) {
        if (file.name.startsWith(templatePrefix)) {
            val key = file.name.substringAfter(templatePrefix)
            val destination: File = file.resolveSibling(promptingMap[key].replace('.', '/'))
            destination.mkdirs()
            check(file.renameTo(destination)) {
                "Failed to rename $file to $destination"
            }
            return@doRecursively destination
        }
    } else {
        if (file.name == "!empty_dir_placeholder!") {
            file.delete()
            return@doRecursively null
        }
        if (file.extension == "file_template") {
            val newFile = file.resolveSibling(file.nameWithoutExtension)
            check(file.renameTo(newFile))
            val initialCode = newFile.readText()
            var code = initialCode
            val opening = "€{"
            val closing = '}'
            while (true) {
                val openingIndex = code.indexOf(opening)
                if (openingIndex == -1) break
                val closingIndex = code.indexOf(closing).also { check(it != -1) }
                val keyStartIndex = openingIndex + opening.length
                val key = code.substring(keyStartIndex, closingIndex)
                val toBeReplaced = code.substring(openingIndex, closingIndex + 1)
                val replacement = promptingMap[key]
                code = code.replace(toBeReplaced, replacement)
            }
            if (code != initialCode) newFile.writeText(code)
        }
    }
    return@doRecursively null
}

////////////////////////////////////////////////////////////////////////////////////////////////////
/// Update settings.gradle.kts to include newly created module. ////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

val settingsFileText = settingsFile.readText()

val includeForLoopLineOfCode = "/${relativePathName.removeSuffix("/")}"
    .replace("/", ":")
    .let { modulePrefix -> ").forEach { include(\"$modulePrefix:\$it\") }" }

val indexOfTargetLoop = settingsFileText.indexOf(includeForLoopLineOfCode)

val newGradleSettingsFileContent: String = if (indexOfTargetLoop >= 0) {
    val indexOfTargetArray = settingsFileText.lastIndexOf("arrayOf", startIndex = indexOfTargetLoop)
    val indexOfFirstArrayElement = settingsFileText.indexOf(
        char = '\n',
        startIndex = indexOfTargetArray
    ) + 1

    settingsFileText.replaceRange(
        startIndex = indexOfFirstArrayElement,
        endIndex = indexOfTargetLoop - 1,
        replacement = settingsFileText.substring(
            startIndex = indexOfFirstArrayElement,
            endIndex = indexOfTargetLoop - 1
        ).let { arrayLines ->
            val newArrayLines = arrayLines.lines().map {
                if (it.endsWith(',')) it else "$it,"
            } + "    \"$moduleName\","
            newArrayLines.sorted().joinToString(separator = "\n").removeSuffix(",")
        }
    )
} else {

    val lastIncludeIndex = settingsFileText.lastIndexOfAny(
        strings = listOf(").forEach { include(\"", "include(")
    )

    val replacement = """
                |
                |
                |arrayOf(
                |    "$moduleName",
                |$includeForLoopLineOfCode
            """.trimMargin()

    if (lastIncludeIndex >= 0) settingsFileText.indexOf('\n', startIndex = lastIncludeIndex).let {
        val startIndex = if (it >= 0) it else settingsFileText.lastIndex
        settingsFileText.replaceRange(
            startIndex = startIndex,
            endIndex = startIndex,
            replacement = replacement
        )
    } else settingsFileText.replaceRange(
        startIndex = settingsFileText.lastIndex,
        endIndex = settingsFileText.lastIndex,
        replacement = replacement
    )
}

settingsFile.writeText(newGradleSettingsFileContent)

////////////////////////////////////////////////////////////////////////////////////////////////////
/// Ask to put module name in clipboard. ///////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

fun putInClipboard(text: String) {
    Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(text), null)
    TODO("Code above throws in command line, complaining it is headless. Find an alternative.")
}

@Suppress("UNREACHABLE_CODE")
runOrRetry {
    return@runOrRetry //TODO: Remove when putInClipboard is fixed.
    println("Do you want to put the module name in clipboard?")
    println("(Press enter without entering anything to skip.)")
    println()
    println("1. Yes, with quotes")
    println("2. Yes, without quotes")
    println("3. No thanks")
    when (readLine()!!.toInt()) {
        1 -> putInClipboard("\"$moduleName\"")
        2 -> putInClipboard(moduleName)
        3 -> Unit
    }
}

println("We're done! Don't forget to setup dependencies manually if needed.")
println("Have a nice day!")

//Idea: Support loop prompt for api and implementation dependencies with set of existing values in number choices.
//Idea: Support optional directories and files.
