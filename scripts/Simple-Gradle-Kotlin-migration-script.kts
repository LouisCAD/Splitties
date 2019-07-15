/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import java.io.File

println("Let's migrate the gradle files of this project from Groovy to Kotlin.")
val dir = File(".").parentFile!!
val ignoredRootDirNames =
    listOf("build", "buildSrc", "gradle", "old-dot-gradle", "projectFilesBackup")

val moduleDirectories: List<File> = dir.listFiles { file: File ->
    file.isDirectory &&
            !file.name.startsWith('.') &&
            file.name !in ignoredRootDirNames &&
            file.listFiles { it: File -> it.isDirectory && it.name == "src" }.size == 1
}!!.sortedBy { it.name }

val nonModuleDirNames = listOf("src", "build", "libs")

fun File.findGradleBuildFiles(): List<File> = listFiles { file: File ->
    file.name == "build.gradle"
}.asList() + listFiles { file: File ->
    file.isDirectory && !file.name.startsWith('.') && file.name !in nonModuleDirNames
}.flatMap { it.findGradleBuildFiles() }

val gradleBuildFiles: List<File> = moduleDirectories.flatMap {
    it.findGradleBuildFiles()
} + (dir.resolve("build.gradle").takeIf { it.exists() }?.let { listOf(it) } ?: emptyList())

val supposedlyNonAmbiguousReplacements: List<Pair<String, String>> = listOf(
    """apply plugin: "com.android.library"
apply plugin: "kotlin-android"""" to """plugins {
    id("com.android.library")
    kotlin("android")
}""",
    """android {
""" to """android {
    val projectSdk_version: Int by extra
    val projectBuildTools_version: String by extra
    val library_version: String by extra
""",
    "minifyEnabled" to "isMinifyEnabled",
    """sourceSets {
        main.java.srcDirs += "src/main/kotlin"
    }""" to """sourceSets {
        names.forEach { getByName(it).java.srcDir("src/${"$"}it/kotlin") }
    }""",
    """apply from: "../publish.gradle"""" to """apply {
    from("../../publish.gradle")
}""",
    "tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {" to
            "tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().whenTaskAdded {"
)

val supposedlyNonAmbiguousStringLiteralLessReplacements: List<Pair<String, String>> = listOf(
    "release" to "getByName(\"release\")",
    "debug" to "getByName(\"debug\")"
)

val supposedlyNonAmbiguousFunctionCalls: List<String> = listOf(
    "compileSdkVersion", "buildToolsVersion", "minSdkVersion", "targetSdkVersion",
    "api", "implementation", "compileOnly", "testImplementation", "androidTestImplementation",
    "classpath", "consumerProguardFiles"
)

val supposedlyNonAmbiguousPropertySettings: List<String> = listOf(
    "versionCode", "versionName", "testInstrumentationRunner", "isMinifyEnabled"
)

sealed class CodeOrComment(val value: String) {
    class Code(value: String) : CodeOrComment(value)
    class Comment(value: String) : CodeOrComment(value)
    companion object {
        val commentStart = listOf("//", "/*")
    }
}

sealed class ScriptRegion(val value: String) {
    class StringLiteralLessCode(value: String) : ScriptRegion(value)
    class StringLiteral(value: String) : ScriptRegion(value) {
        init {
            require(value.first() == '"' && value.last() == '"' && value.length >= 2) {
                "String literal needs to include the quotes."
            }
        }
    }

    class Comment(value: String) : ScriptRegion(value)

    companion object {
        val nonCodeRegionStart = listOf("\"", "//", "/*")
    }
}

private val lineDelimiters = listOf("\r\n", "\n", "\r")

fun makeQuotesKotlinCompatible(input: String): String {
    var index = 0
    return buildString(capacity = input.length) {
        append(input)
        outerLoop@ while (index < input.length) {
            when (input[index]) {
                '\\' -> index++
                '\'' -> set(index, '\"')
                '/' -> when (input[index + 1]) {
                    '/' -> {
                        val indexOfNextLine = input.indexOfAny(lineDelimiters, index)
                        if (indexOfNextLine == -1) {
                            return@buildString
                        } else {
                            index = indexOfNextLine
                        }
                    }
                    '*' -> { // Groovy doesn't support nested block comments.
                        index += 2
                        blockCommentLoop@ while (index < input.length) {
                            val indexOfNextBlockCommentEnd = input.indexOf("*/", index)
                            when (indexOfNextBlockCommentEnd) {
                                -1 -> return@buildString
                                else -> {
                                    index = indexOfNextBlockCommentEnd + 2
                                    continue@outerLoop
                                }
                            }
                        }
                    }
                }
                '"' -> {
                    index++
                    while (index < input.length) {
                        val indexOfClosingDoubleQuote = input.indexOf('"', index)
                        index = indexOfClosingDoubleQuote + 1
                        if (input[indexOfClosingDoubleQuote - 1] != '\\') continue@outerLoop
                    }
                }
            }
            index++
        }
    }
}

fun splitScriptInRegions(input: String): List<ScriptRegion> {
    val regions = mutableListOf<ScriptRegion>()
    var index = 0
    val nonCodeRegionStart = ScriptRegion.nonCodeRegionStart
    outerLoop@ while (index < input.length) {
        val nonCodeStartIndex = input.indexOfAny(nonCodeRegionStart, startIndex = index)
        if (nonCodeStartIndex == -1) {
            regions += ScriptRegion.StringLiteralLessCode(input.substring(index))
            break@outerLoop
        }
        val codeFragment = input.substring(startIndex = index, endIndex = nonCodeStartIndex)
        regions += ScriptRegion.StringLiteralLessCode(codeFragment)
        when {
            input.startsWith("\"", startIndex = nonCodeStartIndex) -> {//TODO: Ignore escaped quotes
                val endIndex = input.indexOf("\"", startIndex = nonCodeStartIndex + 1)
                require(endIndex != -1) {
                    "The string literal has seemingly no end! " +
                            "Note that escaped quotes are not supported yet."
                }
                index = endIndex + 1
                val stringLiteral =
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                regions += ScriptRegion.StringLiteral(stringLiteral)
            }
            input.startsWith("//", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOfAny(lineDelimiters, startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    val eolLength = if (input.startsWith("\r\n", startIndex = endIndex)) 2 else 1
                    index = endIndex + eolLength
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += ScriptRegion.Comment(comment)
            }
            input.startsWith("/*", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOf("*/", startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    index = endIndex + 2
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += ScriptRegion.Comment(comment)
            }
            else -> throw IllegalStateException()
        }
    }
    return regions
}

fun splitCommentsFromCode(input: String): List<CodeOrComment> {
    val regions = mutableListOf<CodeOrComment>()
    var index = 0
    val commentStart = CodeOrComment.commentStart
    outerLoop@ while (index < input.length) {
        val nonCodeStartIndex = input.indexOfAny(commentStart, startIndex = index)
        if (nonCodeStartIndex == -1) {
            val remainingCode = input.substring(index)
            if (remainingCode.isNotEmpty()) regions += CodeOrComment.Code(remainingCode)
            break@outerLoop
        }
        val codeFragment = input.substring(startIndex = index, endIndex = nonCodeStartIndex)
        if (codeFragment.isNotEmpty()) regions += CodeOrComment.Code(codeFragment)
        when {
            input.startsWith("//", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOfAny(lineDelimiters, startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    val eolLength = if (input.startsWith("\r\n", startIndex = endIndex)) 2 else 1
                    index = endIndex + eolLength
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += CodeOrComment.Comment(comment)
            }
            input.startsWith("/*", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOf("*/", startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    index = endIndex + 2
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += CodeOrComment.Comment(comment)
            }
            else -> throw IllegalStateException()
        }
    }
    return regions
}

fun performBasicGroovyToKotlinMigration(input: String): String {
    val codeOrComments = splitCommentsFromCode(buildString {
        splitScriptInRegions(makeQuotesKotlinCompatible(input)).forEach { region ->
            when (region) {
                is ScriptRegion.StringLiteralLessCode -> {
                    var editedCode = region.value
                    supposedlyNonAmbiguousStringLiteralLessReplacements.forEach { (old, new) ->
                        editedCode = editedCode.replace(old, new)
                    }
                    append(editedCode)
                }
                else -> append(region.value)
            }
        }
    })
    val outputCode = StringBuilder()
    for (codeOrComment in codeOrComments) if (codeOrComment is CodeOrComment.Comment) {
        outputCode.append(codeOrComment.value)
    } else {
        var code = codeOrComment.value
        supposedlyNonAmbiguousReplacements.forEach { (old, new) ->
            code = code.replace(old, new)
        }
        supposedlyNonAmbiguousFunctionCalls.forEach {
            var startIndex = 0
            val searchedSnippet = "$it "
            while (startIndex < code.length) {
                val indexInCode = code.indexOf(searchedSnippet, startIndex)
                if (indexInCode == -1) return@forEach
                val endIndex = code.indexOfAny(
                    strings = lineDelimiters,
                    startIndex = indexInCode + searchedSnippet.length
                ).let { eolIndex ->
                    if (eolIndex == -1) code.lastIndex else eolIndex
                }
                val arguments = code.substring(indexInCode + searchedSnippet.length, endIndex)
                code = code.substring(0, indexInCode) + "$it($arguments)" +
                        code.substring(endIndex, code.length)
                startIndex = endIndex
            }
        }
        supposedlyNonAmbiguousPropertySettings.forEach {
            var startIndex = 0
            val searchedSnippet = "$it "
            while (startIndex < code.length) {
                val indexInCode = code.indexOf(searchedSnippet, startIndex)
                if (indexInCode == -1) return@forEach
                val endIndex = code.indexOfAny(
                    strings = lineDelimiters,
                    startIndex = indexInCode + searchedSnippet.length
                ).let { eolIndex ->
                    if (eolIndex == -1) code.lastIndex else eolIndex
                }
                val arguments = code.substring(indexInCode + searchedSnippet.length, endIndex)
                code = code.substring(0, indexInCode) + "$it = $arguments" +
                        code.substring(endIndex, code.length)
                startIndex = endIndex
            }
        }
        outputCode.append(code)
    }
    return outputCode.toString()
}

gradleBuildFiles.forEach { file ->
    file.writeText(performBasicGroovyToKotlinMigration(file.readText()))
    //check(file.renameTo(file.resolveSibling("${file.name}.kts")))
}
