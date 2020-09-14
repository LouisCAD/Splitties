#!/usr/bin/env kotlin

/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:Repository("https://dl.bintray.com/louiscad/maven")
@file:DependsOn("com.louiscad.incubator:lib-publishing-helpers:0.1.3")
//@file:Repository("file:///Users/louiscad/.m2/repository")

import java.io.File
import Releasing_main.ReleaseStep.*
import lib_publisher_tools.cli.CliUi
import lib_publisher_tools.cli.defaultImpl
import lib_publisher_tools.cli.runUntilSuccessWithErrorPrintingOrCancel
import lib_publisher_tools.vcs.Vcs
import lib_publisher_tools.vcs.checkoutDevelop
import lib_publisher_tools.vcs.git
import lib_publisher_tools.vcs.isOnDevelopBranch
import lib_publisher_tools.vcs.pullFromOrigin
import lib_publisher_tools.vcs.pushToOrigin
import lib_publisher_tools.versioning.StabilityLevel
import lib_publisher_tools.versioning.Version
import lib_publisher_tools.versioning.checkIsValidVersionString
import lib_publisher_tools.versioning.stabilityLevel

val dir = File(".")

val cliUi = CliUi.defaultImpl
val git = Vcs.git

fun File.checkChanged() = check(git.didFileChange(this)) {
    "Expected changes in the following file: $this"
}

fun checkOnDevelopBranch() {
    check(git.isOnDevelopBranch()) { "Please, checkout the `develop` branch first." }
}

@Suppress("EnumEntryName")
enum class ReleaseStep { // Order of the steps, must be kept right.
    `Change this library version`,
    `Request doc update confirmation`,
    `Request CHANGELOG update confirmation`,
    `Commit "prepare for release" and tag`,
    `Push release to origin`,
    `Request PR submission`,
    `Wait for successful release by CI`,
    `Push tags to origin`,
    `Request PR merge`,
    `Request GitHub release publication`,
    `Update main branch`,
    `Update develop branch from main`,
    `Change this library version back to a dev version`,
    `Commit "prepare next dev version"`,
    `Push, at last`;
}

val ongoingReleaseFile = dir.resolve("ongoing_release.tmp.properties")
val versionsFile = dir.resolve("libraries_version.txt")

inner class OngoingReleaseImpl {
    fun load() = properties.load(ongoingReleaseFile.inputStream())
    fun write() = properties.store(ongoingReleaseFile.outputStream(), null)
    fun clear() = ongoingReleaseFile.delete()

    private val properties = java.util.Properties()

    var versionBeforeRelease: String by properties
    var newVersion: String by properties

    var currentStepName: String by properties
}

//TODO: Make OngoingRelease and object again when https://youtrack.jetbrains.com/issue/KT-19423 is fixed.
@Suppress("PropertyName")
val OngoingRelease = OngoingReleaseImpl()

var startAtStep: ReleaseStep //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed

val versionTagPrefix = "v"

if (ongoingReleaseFile.exists()) {
    OngoingRelease.load()
    startAtStep = ReleaseStep.valueOf(OngoingRelease.currentStepName)
} else {
    checkOnDevelopBranch()
    with(OngoingRelease) {
        versionBeforeRelease = versionsFile.bufferedReader().use { it.readLine() }.also {
            check(it.contains("-dev-")) {
                "The current version needs to be a dev version, but we got: $it"
            }
        }
        newVersion = askNewVersionInput(
            currentVersion = versionBeforeRelease,
            tagPrefix = versionTagPrefix
        )
        startAtStep = ReleaseStep.values().first()
    }
}

fun askNewVersionInput(
    currentVersion: String,
    tagPrefix: String
): String = cliUi.runUntilSuccessWithErrorPrintingOrCancel {
    cliUi.printInfo("Current version: $currentVersion")
    cliUi.printQuestion("Please enter the name of the new version you want to release:")
    val input = readLine()?.trimEnd() ?: error("Empty input!")
    input.checkIsValidVersionString()
    when {
        "-dev-" in input -> error("Dev versions not allowed")
        "-SNAPSHOT" in input -> error("Snapshots not allowed")
    }
    val existingVersions = git.getTags().filter {
        it.startsWith(tagPrefix) && it.getOrElse(tagPrefix.length) { ' ' }.isDigit()
    }.sorted().toList()
    check("$tagPrefix$input" !in existingVersions) { "This version already exists!" }
    input
}


fun CliUi.runReleaseStep(step: ReleaseStep) = when (step) {
    `Change this library version` -> {
        checkOnDevelopBranch()
        OngoingRelease.newVersion.let { newVersion ->
            printInfo("Splitties new version: \"$newVersion\"")
            requestUserConfirmation("Confirm?")
            versionsFile.writeText(newVersion)
        }
    }
    `Request doc update confirmation` -> {
        arrayOf(
            "README.md"
        ).forEach { relativePath ->
            do {
                requestManualAction(
                    instructions = "Update the `$relativePath` file with the new version (if needed)," +
                        " and any other changes needed for this release."
                )
                if (git.didFileChange(dir.resolve(relativePath))) {
                    break
                }
                if (askIfYes(
                        yesNoQuestion = "Are you sure the $relativePath file doesn't need to be updated?"
                    )
                ) {
                    break
                }
            } while (true)
        }.also {
            if (askIfYes(
                    yesNoQuestion = "Apart from the changelog, is there other files that " +
                        "need to be updated for this new release?"
                )
            ) {
                requestManualAction(
                    instructions = "Let's ensure all these other files are updated."
                )
            }
        }
    }
    `Request CHANGELOG update confirmation` -> {
        requestManualAction("Update the `CHANGELOG.md` for the impending release.")
        dir.resolve("CHANGELOG.md").checkChanged()
    }
    `Commit "prepare for release" and tag` -> with(OngoingRelease) {
        git.commitAllFiles(commitMessage = "Prepare for release $newVersion")
        git.tagAnnotated(tag = "$versionTagPrefix$newVersion", annotationMessage = "Version $newVersion")
    }
    `Push release to origin` -> {
        printInfo("Will now push to origin repository")
        printInfo("Once complete, GitHub should kick-off the release GitHub Action that will perform the publishing.")
        requestUserConfirmation("Continue?")
        git.pushToOrigin()
    }
    `Request PR submission` -> {
        requestManualAction("Create a pull request from the `develop` to the `main` branch on GitHub for the new version, if not already done.")
    }
    `Wait for successful release by CI` -> {
        printInfo("To perform this step, we need to wait for the artifacts building and uploading to Bintray.")
        requestUserConfirmation("Did the publishing/release Github Action complete successfully?")
        printInfo("Alright, we take your word.")
    }
    `Push tags to origin` -> {
        printInfo("Will now push with tags.")
        requestUserConfirmation("Continue?")
        git.pushToOrigin(withTags = true)
    }
    `Request PR merge` -> {
        requestManualAction("Merge the pull request for the new version on GitHub.")
    }
    `Request GitHub release publication` -> {
        requestManualAction("Publish release on GitHub with the changelog.")
    }
    `Update main branch` -> {
        printInfo("Will now checkout the `main` branch, pull from GitHub (origin) to update the local `main` branch.")
        requestUserConfirmation("Continue?")
        git.checkoutBranch("main")
        git.pullFromOrigin()
    }
    `Update develop branch from main` -> {
        printInfo("About to checkout the develop branch (and update it from main for merge commits).")
        requestUserConfirmation("Continue?")
        git.checkoutDevelop()
        git.mergeBranchIntoCurrent(sourceBranch = "main")
    }
    `Change this library version back to a dev version` -> {
        val newVersion = Version(OngoingRelease.newVersion)

        val isNewVersionStable: Boolean = newVersion.stabilityLevel().let { level ->
            if (level == StabilityLevel.Stable) true
            else level == StabilityLevel.Unknown && askIfYes(
                yesNoQuestion = "The stabilityLevel of the new release is unknown. Is it a stable one?"
            )
        }
        val nextDevVersion: String = if (isNewVersionStable) {
            printInfo("Congratulations for this new stable release!")
            printInfo("Let's update the library for next development version.")
            runUntilSuccessWithErrorPrintingOrCancel {
                printInfo("Enter the name of the next target version (`-dev-000` will be added automatically)")
                val input = readLine()
                input.checkIsValidVersionString()
                when (Version(input).stabilityLevel()) {
                    StabilityLevel.Unknown, StabilityLevel.Stable -> Unit
                    else -> error("You need to enter a stable target version")
                }
                "$input-dev-000"
            }
        } else OngoingRelease.versionBeforeRelease.let {
            val devVersionSuffix = it.substringAfter("-dev-").toInt().let { lastReleaseNumber ->
                (lastReleaseNumber + 1).toString().padStart(length = 3, padChar = '0')
            }
            "${it.substringBefore("-dev-")}-dev-$devVersionSuffix"
        }
        versionsFile.writeText(nextDevVersion)
        printInfo("${versionsFile.path} has been edited with next development version ($nextDevVersion).")
    }
    `Commit "prepare next dev version"` -> git.commitAllFiles(
        commitMessage = "Prepare next development version.".also {
            requestUserConfirmation(
                yesNoQuestion = """Will commit all files with message "$it" Continue?"""
            )
        }
    )
    `Push, at last` -> {
        requestUserConfirmation("Finally the last step: the last push. Continue?")
        git.pushToOrigin()
    }
}

fun performRelease() {
    var stepIndex = startAtStep.ordinal
    val enumValues = enumValues<ReleaseStep>().toList()
    while (stepIndex < enumValues.size) {
        val step = enumValues[stepIndex]
        OngoingRelease.currentStepName = step.name
        OngoingRelease.write()
        cliUi.runReleaseStep(step)
        stepIndex++
    }
    OngoingRelease.clear()
    cliUi.printQuestion("All Done! Let's brag about this new release!!")
}

performRelease()
