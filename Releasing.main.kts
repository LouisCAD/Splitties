#!/usr/bin/env kotlin

/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:Repository("https://dl.bintray.com/louiscad/maven")
@file:DependsOn("com.louiscad.incubator:lib-publishing-helpers:0.1.2")
//@file:Repository("file:///Users/louiscad/.m2/repository")

import java.io.File
import Releasing_main.BintrayReleaseStep.*
import lib_publisher_tools.cli.CliUi
import lib_publisher_tools.cli.defaultImpl
import lib_publisher_tools.vcs.Vcs
import lib_publisher_tools.vcs.checkoutDevelop
import lib_publisher_tools.vcs.checkoutMaster
import lib_publisher_tools.vcs.git
import lib_publisher_tools.vcs.isOnDevelopBranch
import lib_publisher_tools.vcs.mergeMasterIntoCurrent
import lib_publisher_tools.vcs.pullFromOrigin
import lib_publisher_tools.vcs.pushToOrigin
import lib_publisher_tools.versioning.checkIsValidVersionString

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
enum class BintrayReleaseStep { // Order of the steps, must be kept right.
    `Change this library version`,
    `Request README update confirmation`,
    `Request CHANGELOG update confirmation`,
    `Commit "prepare for release" and tag`,
    `Push release to origin`,
    `Request PR submission`,
    `Request bintray publish`,
    `Push tags to origin`,
    `Request PR merge`,
    `Request GitHub release publication`,
    `Update master branch`,
    `Update develop branch from master`,
    `Change this library version back to a dev version`,
    `Commit "prepare next dev version"`,
    `Push, at last`;
}

var currentDevVersion: String //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed
var newVersion: String //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed
var startAtStep: BintrayReleaseStep //TODO: Make a val again when https://youtrack.jetbrains.com/issue/KT-20059 is fixed

val ongoingReleaseFile = dir.resolve("ongoing_release.splitties")
val versionsFile = dir.resolve("libraries_version.properties")
val libVersionLineStart = "splitties.version="

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
        libraryVersionLine.substring(startIndex = libVersionLineStart.length).also { versionName ->
            check("-dev-" in versionName) {
                "Version in ${versionsFile.path} should be a `-dev-` version."
            }
        }
    }
    cliUi.printInfo("Current version: $currentDevVersion")
    cliUi.printQuestion("Please enter the name of the new version you want to release:")
    newVersion = readLine()?.trimEnd().also { input ->
        input.checkIsValidVersionString()
        when {
            "-dev-" in input -> throw IllegalStateException("Dev versions not allowed")
            "-SNAPSHOT" in input -> throw IllegalStateException("Snapshots not allowed")
        }
        val existingVersions = git.getTags().filter {
            it.startsWith("v") && it.getOrElse(1) { ' ' }.isDigit()
        }.sorted().toList()
        check("v$input" !in existingVersions) { "This version already exists!" }
    }!!
    startAtStep = `Change this library version`
}


fun runBintrayReleaseStep(step: BintrayReleaseStep) = when (step) {
    `Change this library version` -> {
        checkOnDevelopBranch()
        cliUi.printInfo("New version: \"$newVersion\"")
        cliUi.requestUserConfirmation("Confirm?")
        val versionsFileTextBeforeEdits = versionsFile.readText()
        val libraryVersionLine = versionsFile.readLines().singleOrNull { line ->
            line.startsWith(libVersionLineStart)
        } ?: throw IllegalStateException("Library version line not found.")
        versionsFile.writeText(
            versionsFileTextBeforeEdits.replace(
                oldValue = libraryVersionLine,
                newValue = "$libVersionLineStart$newVersion"
            )
        )
    }
    `Request README update confirmation` -> {
        cliUi.requestManualAction("Update the `README.md` with the new version and any other changes.")
        dir.resolve("README.md").checkChanged()
    }
    `Request CHANGELOG update confirmation` -> {
        cliUi.requestManualAction("Update the `CHANGELOG.md` for the impending release.")
        dir.resolve("CHANGELOG.md").checkChanged()
    }
    `Commit "prepare for release" and tag` -> {
        git.commitAllFiles(commitMessage = "Prepare for release $newVersion")
        git.tagAnnotated(tag = "v$newVersion", annotationMessage = "Version $newVersion")
    }
    `Push release to origin` -> {
        cliUi.printInfo("Will now push to origin repository")
        cliUi.printInfo("Once complete, GitHub should kick-off the release GitHub Action that will perform the publishing.")
        cliUi.requestUserConfirmation("Continue?")
        git.pushToOrigin()
    }
    `Request PR submission` -> {
        cliUi.requestManualAction("Create a pull request from the `develop` to the `master` branch on GitHub for the new version, if not already done.")
    }
    `Request bintray publish` -> {
        cliUi.printInfo("To perform this step, we need to wait for the artifacts building and uploading to Bintray.")
        cliUi.requestUserConfirmation("Did the publishing/release Github Action complete successfully?")
        cliUi.printInfo("Alright, we take your word. Let's release the artifacts now:")
        cliUi.requestManualAction("Sign in on Bintray and publish the packages.")
    }
    `Push tags to origin` -> {
        cliUi.printInfo("Will now push with tags.")
        cliUi.requestUserConfirmation("Continue?")
        git.pushToOrigin(withTags = true)
    }
    `Request PR merge` -> {
        cliUi.requestManualAction("Merge the pull request for the new version on GitHub.")
    }
    `Request GitHub release publication` -> {
        cliUi.requestManualAction("Publish release on GitHub with the changelog.")
    }
    `Update master branch` -> {
        cliUi.printInfo("Will now checkout the `master` branch, pull from GitHub (origin) to update the local `master` branch.")
        cliUi.requestUserConfirmation("Continue?")
        git.checkoutMaster()
        git.pullFromOrigin()
    }
    `Update develop branch from master` -> {
        cliUi.printInfo("About to checkout the develop branch (and update it from master for merge commits).")
        cliUi.requestUserConfirmation("Continue?")
        git.checkoutDevelop()
        git.mergeMasterIntoCurrent()
    }
    `Change this library version back to a dev version` -> {
        cliUi.printInfo("Let's update the library for next development version.")
        cliUi.printInfo("If you want to keep using $currentDevVersion, enter an empty line.")
        cliUi.printInfo("Otherwise, enter the name of the next target version (`-dev-001` will be added automatically)")
        val nextDevVersion: String = readLine().let { input ->
            if (input.isNullOrBlank()) currentDevVersion else "$input-dev-001"
        }
        versionsFile.writeText(
            versionsFile.readText().replace(
                oldValue = "$libVersionLineStart$newVersion",
                newValue = "$libVersionLineStart$nextDevVersion"
            )
        )
        cliUi.printInfo("${versionsFile.path} has been edited with next development version ($nextDevVersion).")
    }
    `Commit "prepare next dev version"` -> git.commitAllFiles(
        commitMessage = "Prepare next development version.".also {
            cliUi.requestUserConfirmation(
                yesNoQuestion = """Will commit all files with message "$it" Continue?"""
            )
        }
    )
    `Push, at last` -> {
        cliUi.requestUserConfirmation("Finally the last step: the last push. Continue?")
        git.pushToOrigin()
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
    cliUi.printQuestion("All Done! Let's brag about this new release!!")
}

releaseOnBintray()
