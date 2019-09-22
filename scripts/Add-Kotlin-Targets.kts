/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// To make the IDE recognize this file, copy-paste it in a Scratch File (cmd/ctrl + shift + N).

println("Welcome in Create new module by Louis CAD")

val targets: List<KotlinTarget> = run {
    val noJvm = listOf("allButJvm", "allButAndroid", "allButNonAndroidJvm")
    val native = noJvm + "native"
    val apple = noJvm + listOf("native", "apple", "apple64")
    val linux = native + "linux"
    val linux64 = linux + "linux64"
    val linux32 = linux + "linux32"
    val mingw = native + "mingw"
    listOf(
        KotlinTarget("android", listOf("allJvm", "allButNonAndroidJvm")),
        KotlinTarget("jvm", listOf("allJvm", "allButAndroid")),
        KotlinTarget("js", noJvm),
        KotlinTarget("ios", apple),
        KotlinTarget("macos", apple),
        KotlinTarget("androidNative", native),
        KotlinTarget("linuxX64", linux64),
        KotlinTarget("linuxArm64", linux64),
        KotlinTarget("linuxArm32Hfp", linux32),
        KotlinTarget("linuxMips32", linux32),
        KotlinTarget("linuxMipsel32", linux32),
        KotlinTarget("mingwX64", mingw),
        KotlinTarget("mingwX86", mingw)
    )
}

val currentPath: Path = Paths.get("")

do {
    selectModuleAndAddTargets()
    println()
} while (promptResponseIsYes(question = "Do you want to add a target to another module?"))


println()
println("We're done! Don't forget to setup dependencies manually if needed.")
println("Have a nice day!")

data class KotlinTarget(
    val name: String,
    val optionalIntermediateSourceSets: List<String> = emptyList()
) {
    init {
        require(optionalIntermediateSourceSets.size <= 9) {
            "Targets are currently selected by digit, which are limited to 9."
        }
    }
}

fun selectModuleAndAddTargets() {
    val modulePath: Path = runOrRetry {
        print("Please type the relative path of the new module (target directory, ")
        println("name of module excluded), or leave empty to use this directory as root.")

        val relativePathName = (readLine() ?: "").let {
            if (it.endsWith('/') || it.isEmpty()) it else "$it/"
        }
        println()
        println("Please enter the name of the module you want to add a target to:")
        val moduleName = readLine()!!
        require(moduleName.isNotBlank()) { "Name entered is blank" }
        //TODO: Check name is valid
        return@runOrRetry currentPath.resolve(relativePathName + moduleName).also {
            check(Files.exists(it)) { "Didn't find the requested module $moduleName." }
            check(Files.isDirectory(it)) { "Expected a directory but found a file" }
        }
    }
    val packageName: String = runOrRetry {
        println()
        println("Please enter/confirm the package name:")
        return@runOrRetry readLine()!!.also {
            check(it.isNotEmpty()) { "Package name is required and can't be empty." }
            check(it.all { c -> c.isLetterOrDigit() || c == '.' || c == '_' }) {
                "A package name can have only letters, digits, dots and underscores."
            }
            check(it.startsWith('.').not()) { "A package name can't start with a dot." }
            check(".." !in it) { "A package name can't have contiguous dots." }
            check(it.first().isDigit().not()) {
                "Package can't start with a digit. Use an underscore or a letter."
            }
            it.reduce { acc, c ->
                if (acc == '.') check(c.isDigit().not()) {
                    "Subpackages can't start with a digit. Use an underscore or a letter."
                }
                return@reduce c
            }
        }
    }
    do {
        addTarget(modulePath, packageName)
        println()
    } while (promptResponseIsYes(question = "Do you want to add another target to this module?"))
}

fun addTarget(modulePath: Path, packageName: String) {

    val srcPath = modulePath.resolve("src")
    val gradleKtsBuildFile = modulePath.resolve("build.gradle.kts").also {
        check(Files.exists(it)) { "build.gradle.kts file is missing" }
    }

    println("Please, select the target you want to add:\n")
    targets.forEachIndexed { i, (name, _) ->
        println("${i + 1}. $name")
    }
    val selectedTarget: KotlinTarget = targets[readLine()!!.toInt() - 1]

    println("Reading build.gradle.kts")
    Files.newBufferedReader(gradleKtsBuildFile).readText().let { text ->
        val kotlinLambdaIndex = text.indexOf("\nkotlin {").also { i ->
            check(i >= 0) { "Didn't find the start of the kotlin lambda" }
        }
        val indexOfLineAfter = text.indexOf('\n', startIndex = kotlinLambdaIndex + 1).also { i ->
            check(i >= 0) { "Didn't find a new line after the start of the kotlin lambda" }
        }
        if (text.indexOf("\n    ${selectedTarget.name}", startIndex = indexOfLineAfter) >= 0) {
            println("Already includes target, continuins.")
        } else {
            val newText = text.replaceRange(
                startIndex = indexOfLineAfter,
                endIndex = indexOfLineAfter,
                replacement = "\n    ${selectedTarget.name}()"
            )
            println("Writing updated build.gradle.kts with new target")
            Files.newBufferedWriter(gradleKtsBuildFile).use {
                it.write(newText)
                it.flush()
            }
            println("Write complete")
        }
    }

    val targetMainSourceSetPath: Path = srcPath.resolve("${selectedTarget.name}Main")
    val targetTestSourceSetPath: Path = srcPath.resolve("${selectedTarget.name}Test")
    logSingleFileCreation(what = "Directory for ${selectedTarget.name} target") {
        Files.createDirectory(targetMainSourceSetPath)
    }

    val createdIntermediateSourceSetsCount = promptIntermediateSourceSetsCreation(
        selectedTarget = selectedTarget,
        targetMainSourceSetPath = targetMainSourceSetPath,
        targetTestSourceSetPath = targetTestSourceSetPath,
        srcPath = srcPath,
        packageName = packageName
    )

    if (createdIntermediateSourceSetsCount == 0 || promptResponseIsYes(
            question = "Will you also add Kotlin source files directly into ${selectedTarget.name}?"
        )
    ) {
        logSingleFileCreation(what = "Package name directories") {
            Files.createDirectories(
                targetMainSourceSetPath.resolve("kotlin/${packageName.replace('.', '/')}")
            )
        }
        if (promptResponseIsYes("Test files too?")) {
            logSingleFileCreation(what = "Package name directories for tests") {
                Files.createDirectories(
                    targetTestSourceSetPath.resolve("kotlin/${packageName.replace('.', '/')}")
                )
            }
        }
    }

    println()
    println("Done")
}

/**
 * Prompts the user which intermediate source sets (if any) he wants, creates the directories along
 * with their symlinks, and returns how many were created.
 */
fun promptIntermediateSourceSetsCreation(
    selectedTarget: KotlinTarget,
    targetMainSourceSetPath: Path,
    targetTestSourceSetPath: Path,
    srcPath: Path,
    packageName: String
): Int {
    val optionalIntermediateSourceSets = selectedTarget.optionalIntermediateSourceSets
    if (optionalIntermediateSourceSets.isEmpty()) return 0
    println()
    println("Please, type all the digits of the source sets you want to symlink,")
    println("and append an exclamation mark if you don't want a test directory to be created.")
    println("For example, type \"1!23\" for choices 1, 2 and 3, or \"23\" for 2 and 3 with")
    println("choice 1 without a test directory.")
    println("Digits of your choices:")
    optionalIntermediateSourceSets.forEachIndexed { i, it ->
        println("${i + 1}. $it")
    }
    val choices = readLine()!!
    return optionalIntermediateSourceSets.withIndex().count { (i, intermediateSourceSet) ->
        val digit = (i + 1).toString().single()
        val choiceIndex = choices.indexOf(digit)
        val isAmongChoices = choiceIndex >= 0
        if (isAmongChoices.not()) return@count false
        val intermediateSourceSetMain = "${intermediateSourceSet}Main"
        logSingleFileCreation(
            what = "Symbolic link for $intermediateSourceSetMain in ${selectedTarget.name}"
        ) {
            Files.createSymbolicLink(
                targetMainSourceSetPath.resolve(intermediateSourceSetMain),
                Paths.get("../$intermediateSourceSetMain")
            )
        }
        logSingleFileCreation("Package name directories for $intermediateSourceSetMain") {
            Files.createDirectories(
                srcPath.resolve(
                    "$intermediateSourceSetMain/kotlin/${packageName.replace('.', '/')}"
                )
            )
        }
        val skipTestsDir = choices.getOrElse(choiceIndex + 1) { '0' } == '!'
        if (skipTestsDir) return@count true
        val intermediateSourceSetTest = "${intermediateSourceSet}Test"
        logSingleFileCreation(
            what = "Test directory for ${selectedTarget.name} target",
            printAlreadyExists = false
        ) {
            Files.createDirectory(targetTestSourceSetPath)
        }
        logSingleFileCreation(
            what = "Symbolic link for $intermediateSourceSetTest in ${selectedTarget.name}"
        ) {
            Files.createSymbolicLink(
                targetTestSourceSetPath.resolve(intermediateSourceSetTest),
                Paths.get("../$intermediateSourceSetTest")
            )
        }
        logSingleFileCreation("Package name directories for $intermediateSourceSetTest") {
            Files.createDirectories(
                srcPath.resolve(
                    "$intermediateSourceSetTest/kotlin/${packageName.replace('.', '/')}"
                )
            )
        }
        return@count true
    }
}

inline fun logSingleFileCreation(
    what: String,
    printAlreadyExists: Boolean = true,
    block: () -> Unit
) {
    try {
        block()
        println("$what created successfully")
    } catch (ignored: FileAlreadyExistsException) {
        if (printAlreadyExists) println("$what already exists, continuing…")
    }
}

inline fun <R> runOrRetry(block: () -> R): R {
    while (true) try {
        return block()
    } catch (t: Exception) {
        if (t is NullPointerException) throw t // Avoid infinite loop if readLine returns null
        println(t.message ?: t)
    }
}

fun promptResponseIsYes(question: String): Boolean {
    while (true) {
        println("$question Y/n")
        when (readLine()) {
            "Y", "yes", "Yes", "YES" -> return true
            "n", "N", "No", "NO" -> return false
            "y" -> println("Type yes or an uppercase Y letter.")
            else -> println("Unexpected answer, please answer Y(es) or n(o).")
        }
    }
}
