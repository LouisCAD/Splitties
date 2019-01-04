import java.io.File
import java.util.concurrent.TimeUnit
import java.net.URI

val dir = File(".")

fun String.execute(workingDir: File = dir): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(60, TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText()
}

println("git describe --dirty".execute())

fun releasingNonSnapshot() {
    val currentBranch = "git rev-parse --abbrev-ref HEAD".execute().trimEnd()
    check(currentBranch == "develop") { "Please, checkout the `develop` branch first." }
    val versionsFile = dir.resolve("buildSrc/src/main/kotlin/ProjectVersions.kt")
    val libVersionLineStart = "    const val thisLibrary = \""
    val libraryVersionLine = versionsFile.readLines().singleOrNull { line ->
        line.startsWith(libVersionLineStart)
    } ?: throw IllegalStateException("Library version line not found.")
    println(
        "Current version: ${libraryVersionLine.substring(
            startIndex = libVersionLineStart.length,
            endIndex = libraryVersionLine.lastIndex
        )}"
    )
    println("Please enter the name of the new version you want to release:")
    val newVersion = readLine()?.trimEnd().also { input ->
        when {
            input.isNullOrEmpty() -> throw IllegalStateException("No version entered.")
            input.any { it == ' ' } -> throw IllegalStateException("Versions can't contain spaces.")
            input.startsWith('v') -> throw IllegalStateException("Please, don't include v prefix.")
            !input.first().isDigit() -> throw IllegalStateException("Should start with a digit.")
            !input.all {
                it.isLetterOrDigit() || it == '.' || it == '-'
            } -> throw IllegalStateException("Only digits, letters, dots and dashes are allowed.")
            !input.contains("-SNAPSHOT") -> throw IllegalStateException("Snapshots not allowed")
        }
        val existingVersions = "git tag".execute().trimEnd().lineSequence().filter {
            it.startsWith("v") && it.getOrElse(1) { ' ' }.isDigit()
        }.sorted().toList()
        check("v$input" !in existingVersions) { "This version already exists!" }
    }!!
    println("New version: \"$newVersion\"")
    println("Confirm? Y/n")
    readLine()?.trimEnd().let { input ->
        if (input != "Y" && !"yes".equals(input, ignoreCase = true)) {
            println("Process aborted."); return
        }
    }
    versionsFile.writeText(versionsFile.readText().replace(
        oldValue = libraryVersionLine,
        newValue = "$libVersionLineStart$newVersion\""
    ))
    /*
    3. Update the `README.md` with the new version.
        4. Update the `CHANGELOG.md` for the impending release.
        5. Run `git commit -am "Prepare for release X.Y.Z"` (where X.Y.Z is the new version).
        6. `git tag -a vX.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version).
        7. Run `./gradlew clean bintrayUpload`.
    8. Run `git push origin`.
    9. Create a pull request from the `develop` to the `master` branch on GitHub for the new version, if not already done.
    10. Sign in on Bintray and publish the packages.
    11. Run `git push origin --tags`.
    12. Merge the pull request for the new version on GitHub.
        13. Publish release on GitHub.
    14. Checkout the `master` branch.
    15. Pull from GitHub repository to update the local `master` branch.
    16. Checkout the `develop` branch.
    17. Change the `thisLibrary` constant in the
            [ProjectVersions](buildSrc/src/main/kotlin/ProjectVersions.kt) file back to a SNAPSHOT version.
        18. Run `git commit -am "Prepare next development version."`.
    19. Run `git push origin`.*/
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

releasingNonSnapshot()
//openUrl("https://stackoverflow.com/a/48266060/4433326")
