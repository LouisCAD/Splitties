/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import java.io.File
import java.util.concurrent.TimeUnit
import java.net.URI
import Releasing.BintrayReleaseStep.*
import java.util.concurrent.CancellationException
import java.util.regex.Matcher
import java.util.regex.Pattern

val dir = File(".")

fun processBuilder(rawCommand: String, workingDir: File = dir): ProcessBuilder {
    val command = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(rawCommand).let { m ->
        generateSequence {
            when {
                m.find() -> if (m.group(1) != null) m.group(1) else m.group(2)
                else -> null
            }
        }
    }.toList()
    return ProcessBuilder(command)
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
}

fun String.execute(workingDir: File = dir): String {
    val proc = processBuilder(
        rawCommand = this,
        workingDir = workingDir
    ).start()
    proc.waitFor(60, TimeUnit.MINUTES)
    return proc.inputStream.use { it.bufferedReader().readText() }.also {
        val exitValue = proc.exitValue()
        if (exitValue != 0) {
            throw Exception("Non zero exit value: $exitValue")
        }
    }
}

fun String.executeAndPrint(workingDir: File = dir) {
    val proc = processBuilder(rawCommand = this, workingDir = workingDir)
        .redirectInput(ProcessBuilder.Redirect.INHERIT)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
    proc.waitFor(60, TimeUnit.MINUTES)
    val exitValue = proc.exitValue()
    if (exitValue != 0) {
        throw Exception("Non zero exit value: $exitValue")
    }
}

fun File.checkChanged() {
    try {
        "git diff HEAD --exit-code $this".execute()
    } catch (ignored: Exception) {
        return // Exit code is 1 (translated to an exception) when file changed
    }
    error("Expected changes in the following file: $this") // Exit code is 0 if not changed.
}

enum class AnsiColor(private val colorNumber: Byte) {
    BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), WHITE(7);

    companion object {
        private const val prefix = "\u001B"
        const val RESET = "$prefix[0m"
        private val isCompatible = "win" !in System.getProperty("os.name").toLowerCase()
    }

    val regular get() = if (isCompatible) "$prefix[0;3${colorNumber}m" else ""
    val bold get() = if (isCompatible) "$prefix[1;3${colorNumber}m" else ""
    val underline get() = if (isCompatible) "$prefix[4;3${colorNumber}m" else ""
    val background get() = if (isCompatible) "$prefix[4${colorNumber}m" else ""
    val highIntensity get() = if (isCompatible) "$prefix[0;9${colorNumber}m" else ""
    val boldHighIntensity get() = if (isCompatible) "$prefix[1;9${colorNumber}m" else ""
    val backgroundHighIntensity get() = if (isCompatible) "$prefix[0;10${colorNumber}m" else ""
}

fun printInfo(message: String) {
    print(AnsiColor.WHITE.boldHighIntensity)
    print(AnsiColor.GREEN.background)
    print(message)
    println(AnsiColor.RESET)
}

fun printQuestion(message: String) {
    print(AnsiColor.WHITE.boldHighIntensity)
    print(AnsiColor.BLUE.background)
    print(message)
    println(AnsiColor.RESET)
}

fun requestManualAction(instructions: String) {
    print(AnsiColor.WHITE.boldHighIntensity)
    print(AnsiColor.BLUE.background)
    print(instructions)
    println(AnsiColor.RESET)
    requestUserConfirmation("Done?")
}

fun requestUserConfirmation(yesNoQuestion: String) {
    print(AnsiColor.WHITE.boldHighIntensity)
    print(AnsiColor.BLUE.background)
    print("$yesNoQuestion Y/n")
    println(AnsiColor.RESET)
    readLine()?.trimEnd().let { input ->
        if (input != "Y" && !"yes".equals(input, ignoreCase = true)) {
            println("Process aborted."); throw CancellationException()
        }
    }
}

enum class BintrayReleaseStep { // Order of the steps, must be kept right.
    CHANGE_THIS_LIBRARY_CONSTANT,
    REQUEST_README_UPDATE_CONFIRMATION,
    REQUEST_CHANGELOG_UPDATE_CONFIRMATION,
    COMMIT_PREPARE_FOR_RELEASE_AND_TAG,
    CLEAN_AND_UPLOAD,
    PUSH_RELEASE_TO_ORIGIN,
    REQUEST_PR_SUBMISSION,
    REQUEST_BINTRAY_PUBLISH,
    PUSH_TAGS_TO_ORIGIN,
    REQUEST_PR_MERGE,
    REQUEST_GITHUB_RELEASE_PUBLICATION,
    UPDATE_MASTER_BRANCH,
    UPDATE_DEVELOP_BRANCH_FROM_MASTER,
    CHANGE_THIS_LIBRARY_CONSTANT_BACK_TO_A_DEV_VERSION,
    COMMIT_PREPARE_NEXT_DEV_VERSION,
    PUSH_AT_LAST;
}

fun checkOnDevelopBranch() {
    val currentBranch = "git rev-parse --abbrev-ref HEAD".execute().trimEnd()
    check(currentBranch == "develop") { "Please, checkout the `develop` branch first." }
}

var currentDevVersion: String //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed
var newVersion: String //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed
var startAtStep: BintrayReleaseStep //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed

val ongoingReleaseFile = dir.resolve("ongoing_release.splitties")
val versionsFile = dir.resolve("buildSrc/src/main/kotlin/ProjectVersions.kt")
val libVersionLineStart = "    const val thisLibrary = \""

if (ongoingReleaseFile.exists()) {
    ongoingReleaseFile.readLines().let {
        currentDevVersion = it[0]
        newVersion = it[1]
        startAtStep = BintrayReleaseStep.valueOf(it[2])
    }
} else {
    checkOnDevelopBranch()
    currentDevVersion = let { _ ->
        val libraryVersionLine = versionsFile.readLines().singleOrNull { line ->
            line.startsWith(libVersionLineStart)
        } ?: throw IllegalStateException("Library version line not found.")
        libraryVersionLine.substring(
            startIndex = libVersionLineStart.length,
            endIndex = libraryVersionLine.lastIndex
        ).also { versionName ->
            check("-dev-" in versionName) {
                "Version in ${versionsFile.path} should be a `-dev-` version."
            }
        }
    }
    printInfo("Current version: $currentDevVersion")
    printQuestion("Please enter the name of the new version you want to release:")
    newVersion = readLine()?.trimEnd().also { input ->
        when {
            input.isNullOrEmpty() -> throw IllegalStateException("No version entered.")
            input.any { it == ' ' } -> throw IllegalStateException("Versions can't contain spaces.")
            input.startsWith('v') -> throw IllegalStateException("Please, don't include v prefix.")
            !input.first().isDigit() -> throw IllegalStateException("Should start with a digit.")
            !input.all {
                it.isLetterOrDigit() || it == '.' || it == '-'
            } -> throw IllegalStateException("Only digits, letters, dots and dashes are allowed.")
            "-dev-" in input -> throw IllegalStateException("Dev versions not allowed")
            "-SNAPSHOT" in input -> throw IllegalStateException("Snapshots not allowed")
        }
        val existingVersions = "git tag".execute().trimEnd().lineSequence().filter {
            it.startsWith("v") && it.getOrElse(1) { ' ' }.isDigit()
        }.sorted().toList()
        check("v$input" !in existingVersions) { "This version already exists!" }
    }!!
    startAtStep = CHANGE_THIS_LIBRARY_CONSTANT
}


fun runBintrayReleaseStep(step: BintrayReleaseStep) = when (step) {
    CHANGE_THIS_LIBRARY_CONSTANT -> {
        checkOnDevelopBranch()
        printInfo("New version: \"$newVersion\"")
        requestUserConfirmation("Confirm?")
        val versionsFileTextBeforeEdits = versionsFile.readText()
        val libraryVersionLine = versionsFile.readLines().singleOrNull { line ->
            line.startsWith(libVersionLineStart)
        } ?: throw IllegalStateException("Library version line not found.")
        versionsFile.writeText(
            versionsFileTextBeforeEdits.replace(
                oldValue = libraryVersionLine,
                newValue = "$libVersionLineStart$newVersion\""
            )
        )
    }
    REQUEST_README_UPDATE_CONFIRMATION -> {
        requestManualAction("Update the `README.md` with the new version and any other changes.")
        dir.resolve("README.md").checkChanged()
    }
    REQUEST_CHANGELOG_UPDATE_CONFIRMATION -> {
        requestManualAction("Update the `CHANGELOG.md` for the impending release.")
        dir.resolve("CHANGELOG.md").checkChanged()
    }
    COMMIT_PREPARE_FOR_RELEASE_AND_TAG -> {
        "git commit -am \"Prepare for release $newVersion\"".executeAndPrint()
        "git tag -a v$newVersion -m \"Version $newVersion\"".executeAndPrint()
    }
    CLEAN_AND_UPLOAD -> {
        val cleanAndUploadCommand = "./gradlew clean bintrayUpload"
        printInfo("Running `$cleanAndUploadCommand`")
        cleanAndUploadCommand.executeAndPrint()
        printQuestion("Please check upload succeded.")
    }
    PUSH_RELEASE_TO_ORIGIN -> {
        val pushToOriginCommand = "git push origin"
        printInfo("Will now run $pushToOriginCommand")
        requestUserConfirmation("Continue?")
        pushToOriginCommand.executeAndPrint()
    }
    REQUEST_PR_SUBMISSION -> {
        requestManualAction("Create a pull request from the `develop` to the `master` branch on GitHub for the new version, if not already done.")
    }
    REQUEST_BINTRAY_PUBLISH -> {
        requestManualAction("Sign in on Bintray and publish the packages.")
    }
    PUSH_TAGS_TO_ORIGIN -> {
        val pushToOriginWithTagsCommand = "git push origin --tags"
        printInfo("Will now run $pushToOriginWithTagsCommand")
        requestUserConfirmation("Continue?")
        pushToOriginWithTagsCommand.executeAndPrint()
    }
    REQUEST_PR_MERGE -> {
        requestManualAction("Merge the pull request for the new version on GitHub.")
    }
    REQUEST_GITHUB_RELEASE_PUBLICATION -> {
        requestManualAction("Publish release on GitHub.")
    }
    UPDATE_MASTER_BRANCH -> {
        printInfo("Will now checkout the `master` branch, pull from GitHub (origin) to update the local `master` branch.")
        requestUserConfirmation("Continue?")
        "git checkout master".executeAndPrint()
        "git pull origin".executeAndPrint()
    }
    UPDATE_DEVELOP_BRANCH_FROM_MASTER -> {
        printInfo("About to checkout the develop branch (and update it from master for merge commits).")
        requestUserConfirmation("Continue?")
        "git checkout develop".executeAndPrint()
        "git merge master".executeAndPrint()
    }
    CHANGE_THIS_LIBRARY_CONSTANT_BACK_TO_A_DEV_VERSION -> {
        printInfo("Let's update the library for next development version.")
        printInfo("If you want to keep using $currentDevVersion, enter an empty line.")
        printInfo("Otherwise, enter the name of the next target version (`-dev-001` will be added automatically)")
        val nextDevVersion: String = readLine().let { input ->
            if (input.isNullOrBlank()) currentDevVersion else "$input-dev-001"
        }
        versionsFile.writeText(
            versionsFile.readText().replace(
                oldValue = "$libVersionLineStart$newVersion\"",
                newValue = "$libVersionLineStart$nextDevVersion\""
            )
        )
        printInfo("${versionsFile.path} has been edited with next developement version ($nextDevVersion).")
    }
    COMMIT_PREPARE_NEXT_DEV_VERSION -> {
        val nextDevVersionCommitCommand = "git commit -am \"Prepare next development version.\""
        requestUserConfirmation("Will run $nextDevVersionCommitCommand Continue?")
        nextDevVersionCommitCommand.executeAndPrint()
    }
    PUSH_AT_LAST -> {
        val pushToOriginCommand = "git push origin"
        requestUserConfirmation("Finally the last step: Running: `$pushToOriginCommand`. Continue?")
        pushToOriginCommand.executeAndPrint()
    }
}

fun releaseOnBintray() {
    var stepIndex = startAtStep.ordinal
    val enumValues = enumValues<BintrayReleaseStep>().toList()
    while (stepIndex < enumValues.size) {
        val step = enumValues[stepIndex]
        ongoingReleaseFile.writeText(buildString {
            appendln(currentDevVersion)
            appendln(newVersion)
            appendln(step.name)
        })
        runBintrayReleaseStep(step)
        stepIndex++
    }
    ongoingReleaseFile.delete()
    printQuestion("All Done! Let's brag about this new release!!")
}

fun openUrl(url: String) {
    val osName = System.getProperty("os.name").toLowerCase()
    val isMacOs: Boolean = "mac" in osName
    val command = if (isMacOs) "open $url" else {
        val isWindows: Boolean = "win" in osName
        if (isWindows) {
            """start "" "$url""""
        } else "xdg-open $url"
    }
    command.execute()
}
releaseOnBintray()
val createAndroidStudioCommandLineLauncherUrl = "https://stackoverflow.com/a/48266060/4433326"
//openUrl(createAndroidStudioCommandLineLauncherUrl)
