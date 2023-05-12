#!/usr/bin/env kotlin

/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Repository("https://repo.maven.apache.org/maven2/")
//@file:Repository("https://oss.sonatype.org/content/repositories/snapshots")
//@file:Repository("file:///Users/me/.m2/repository")
@file:DependsOn("com.louiscad.incubator:lib-publishing-helpers:0.2.3")

import Releasing_main.CiReleaseFailureCause.*
import java.io.File
import Releasing_main.ReleaseStep.*
import lib_publisher_tools.cli.CliUi
import lib_publisher_tools.cli.defaultImpl
import lib_publisher_tools.cli.runUntilSuccessWithErrorPrintingOrCancel
import lib_publisher_tools.vcs.*
import lib_publisher_tools.versioning.StabilityLevel
import lib_publisher_tools.versioning.Version
import lib_publisher_tools.versioning.checkIsValidVersionString
import lib_publisher_tools.versioning.stabilityLevel

val gitHubRepoUrl = "https://github.com/LouisCAD/Splitties"

val dir = File(".")

val publishToMavenCentralWorkflowFilename = "publish-to-maven-central.yml".also {
    check(dir.resolve(".github").resolve("workflows").resolve(it).exists()) {
        "The $it file expected in the `.github/workflows dir wasn't found!\n" +
                "The filename is required to be correct.\n" +
                "If the release workflow needs to be retried, it will be used to make a valid link."
    }
}

val publishToMavenCentralWorkflowLink = "$gitHubRepoUrl/actions/workflows/$publishToMavenCentralWorkflowFilename"

val cliUi = CliUi.defaultImpl
val git = Vcs.git

// The two variables below allow changing reference branches if needed.
val devBranch = "main"
val releaseBranch = "release"

fun File.checkChanged() = check(git.didFileChange(this)) {
    "Expected changes in the following file: $this"
}

fun checkOnDevBranch() {
    check(git.isOnBranch(devBranch)) { "Please, checkout the `$devBranch` branch first." }
}

@Suppress("EnumEntryName")
enum class ReleaseStep { // Order of the steps, must be kept right.
    `Update release branch`,
    `Update dev branch from release`,
    `Change this library version`,
    `Request doc update confirmation`,
    `Request CHANGELOG update confirmation`,
    `Commit 'prepare for release' and tag`,
    `Push release to origin`,
    `Request PR submission`,
    `Wait for successful release by CI`,
    `Push tags to origin`,
    `Request PR merge`,
    `Request GitHub release publication`,
    `Change this library version back to a SNAPSHOT`,
    `Commit 'prepare next dev version'`,
    `Push, at last`;
}

sealed interface CiReleaseFailureCause {
    enum class RequiresNewCommits : CiReleaseFailureCause { BuildFailure, PublishingRejection }
    enum class RequiresRetrying : CiReleaseFailureCause { ThirdPartyOutage, NetworkOutage }
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

fun tagOfVersionBeingReleased(): String = "$versionTagPrefix${OngoingRelease.newVersion}"

if (ongoingReleaseFile.exists()) {
    OngoingRelease.load()
    startAtStep = ReleaseStep.valueOf(OngoingRelease.currentStepName)
} else {
    checkOnDevBranch()
    with(OngoingRelease) {
        versionBeforeRelease = versionsFile.bufferedReader().use { it.readLine() }.also {
            check(it.contains("-dev-") || it.endsWith("-SNAPSHOT")) {
                "The current version needs to be a SNAPSHOT or a dev version, but we got: $it"
            }
        }
        newVersion = askNewVersionInput(
            currentVersion = versionBeforeRelease,
            tagPrefix = versionTagPrefix
        )
    }
    startAtStep = ReleaseStep.values().first()
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


fun CliUi.runReleaseStep(step: ReleaseStep): Unit = when (step) {
    `Update release branch` -> {
        printInfo("Before proceeding to the release, we will ensure we merge changes from the $releaseBranch branch into the $devBranch branch.")
        printInfo("Will now checkout the `$releaseBranch` branch and pull from GitHub (origin) to update the local `$releaseBranch` branch.")
        requestUserConfirmation("Continue?")
        if (git.hasBranch(releaseBranch)) {
            git.checkoutBranch(releaseBranch)
            git.pullFromOrigin()
        } else {
            printInfo("The branch $releaseBranch doesn't exist locally. Fetching from remote…")
            git.fetch()
            if (git.hasRemoteBranch(remoteName = "origin", branchName = releaseBranch)) {
                printInfo("The branch exists on the origin remote. Checking out.")
                git.checkoutAndTrackRemoteBranch("origin", releaseBranch)
            } else {
                printInfo("Creating and checking out the $releaseBranch branch")
                git.createAndCheckoutBranch(releaseBranch)
                printInfo("Pushing the new $releaseBranch branch…")
                git.push(repository = "origin", setUpstream = true, branchName = releaseBranch)
            }
        }
    }
    `Update dev branch from release` -> {
        printInfo("About to checkout the $devBranch branch (and update it from $releaseBranch for merge commits).")
        requestUserConfirmation("Continue?")
        git.checkoutBranch(devBranch)
        git.mergeBranchIntoCurrent(releaseBranch)
    }
    `Change this library version` -> {
        checkOnDevBranch()
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
                    yesNoQuestion = "Apart from the changelog, are there any other files that " +
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
    `Commit 'prepare for release' and tag` -> with(OngoingRelease) {
        git.commitAllFiles(commitMessage = "Prepare for release $newVersion")
        git.tagAnnotated(tag = tagOfVersionBeingReleased(), annotationMessage = "Version $newVersion")
    }
    `Push release to origin` -> {
        printInfo("Will now push to origin repository")
        requestUserConfirmation("Continue?")
        git.pushToOrigin()
    }
    `Request PR submission` -> {
        printInfo("You now need to create a pull request from the `$devBranch` to the `$releaseBranch` branch on GitHub for the new version,")
        printInfo("if not already done.")
        printInfo("You can do so by heading over to the following url:")
        printInfo("$gitHubRepoUrl/compare/$releaseBranch...$devBranch")
        printInfo("Here's a title suggestion which you can copy/paste:")
        printInfo("Prepare for release ${OngoingRelease.newVersion}")
        printInfo("Once submitted, GitHub should kick-off the release GitHub Action that will perform the publishing.")
        requestManualAction("PR submitted?")
    }
    `Wait for successful release by CI` -> {
        printInfo("To perform this step, we need to wait for the artifacts building and uploading.")
        do {
            printInfo("The build and publishing workflow is expected to take between 20 minutes and an hour.")
            printInfo("")
            printInfo("We recommend to set a timer for 30 minutes to not forget to check the status.")
            printInfo("Suggestion: In case it's not complete after that time, set a 10min timer to check again, until completion.")
            val succeeded = askIfYes("Did the publishing/release Github Action complete successfully?")
            if (succeeded.not()) {
                printQuestion("What was the cause of failure?")
                val failureCause: CiReleaseFailureCause = askChoice(
                    optionsWithValues = listOf(
                        "Outage of a third party service (GitHub actions, Sonatype, MavenCentral, Google Maven…)" to RequiresRetrying.ThirdPartyOutage,
                        "Network outage" to RequiresRetrying.NetworkOutage,
                        "Build failure that requires new commits to fix" to RequiresNewCommits.BuildFailure,
                        "Publication was rejected because of misconfiguration" to RequiresNewCommits.PublishingRejection
                    )
                )
                when (failureCause) {
                    is RequiresRetrying -> {
                        printInfo("The outage will most likely be temporary.")
                        when (failureCause) {
                            RequiresRetrying.ThirdPartyOutage -> "You can search for the status page of the affected service and check it periodically."
                            RequiresRetrying.NetworkOutage -> "You can retry when you feel or know it might be resolved."
                        }.let { infoMessage ->
                            printInfo(infoMessage)
                        }
                        printInfo("Once the outage is resolved, head to the following url to run the workflow again, on the right branch:")
                        printInfo(publishToMavenCentralWorkflowLink)
                        requestManualAction("Click the `Run workflow` button, select the `$devBranch` branch and confirm.")
                    }
                    is RequiresNewCommits -> {
                        if (git.hasTag(tagOfVersionBeingReleased())) {
                            printInfo("Removing the version tag (will be put back later on)")
                            git.deleteTag(tag = tagOfVersionBeingReleased())
                            printInfo("tag removed")
                        }
                        printInfo("Recovering from that is going to require new fixing commits to be pushed to the $devBranch branch.")
                        printInfo("Note: you can keep this script waiting while you're resolving the build issue.")
                        requestManualAction("Fix the issues and commit the changes")
                        printInfo("Will now push the new commits")
                        requestUserConfirmation("Continue?")
                        git.pushToOrigin()
                        printInfo("Now, head to the following url to run the workflow again, on the right branch:")
                        printInfo(publishToMavenCentralWorkflowLink)
                        requestManualAction("Click the `Run workflow` button, select the `$devBranch` branch and confirm.")
                    }
                }
            }
        } while (succeeded.not())
        printInfo("Alright, we take your word.")
    }
    `Push tags to origin` -> {
        printInfo("Will now push with tags.")
        requestUserConfirmation("Continue?")
        if (git.hasTag(tagOfVersionBeingReleased()).not()) with(OngoingRelease) {
            printInfo("The tag for the impeding release is missing, so we're putting it back too.")
            git.tagAnnotated(tag = tagOfVersionBeingReleased(), annotationMessage = "Version $newVersion")
        }
        git.pushToOrigin(withTags = true)
    }
    `Request PR merge` -> {
        requestManualAction("Merge the pull request for the new version on GitHub.")
        printInfo("Now that the pull request has been merged into the $releaseBranch branch on GitHub,")
        printInfo("we are going to update our local $releaseBranch branch")
        requestUserConfirmation("Ready?")
        git.updateBranchFromOrigin(targetBranch = releaseBranch)
    }
    `Request GitHub release publication` -> {
        printInfo("It's now time to publish the release on GitHub, so people get notified.")
        printInfo("Copy the section of this release from the CHANGELOG file, and head over to the following url to proceed:")
        printInfo("$gitHubRepoUrl/releases/new")
        requestManualAction("Publish the release ${OngoingRelease.newVersion} on GitHub with the changelog.")
    }
    `Change this library version back to a SNAPSHOT` -> {
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
                printInfo("Enter the name of the next target version (`-SNAPSHOT` will be added automatically)")
                val input = readLine()
                input.checkIsValidVersionString()
                when (Version(input).stabilityLevel()) {
                    StabilityLevel.Unknown, StabilityLevel.Stable -> Unit
                    else -> error("You need to enter a stable target version")
                }
                "$input-SNAPSHOT"
            }
        } else OngoingRelease.versionBeforeRelease.let {
            if (it.endsWith("-SNAPSHOT")) it
            else "${it.substringBefore("-dev-")}-SNAPSHOT"
        }
        versionsFile.writeText(nextDevVersion)
        printInfo("${versionsFile.path} has been edited with next development version ($nextDevVersion).")
    }
    `Commit 'prepare next dev version'` -> git.commitAllFiles(
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
