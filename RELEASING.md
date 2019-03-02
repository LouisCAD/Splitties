# Releasing this library:

## Releasing a stable, beta or alpha version

### Option A: Run the interactive script

Run [Releasing.kts](Releasing.kts) (preferably in system terminal as IDE could crash)
with `kotlinc -script Releasing.kts` and follow the steps directly from the command line.

### Option B: Manual steps

1. Checkout the `develop` branch, if not already done.
2. Change the `thisLibrary` constant in the
[ProjectVersions](buildSrc/src/main/kotlin/ProjectVersions.kt) file to a non-dev version.
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
    [ProjectVersions](buildSrc/src/main/kotlin/ProjectVersions.kt) file back to a `-dev-` version.
18. Run `git commit -am "Prepare next development version."`.
19. Run `git push origin`.

## Publishing a dev version

1. Make sure the `thisLibrary` constant in the
[ProjectVersions](buildSrc/src/main/kotlin/ProjectVersions.kt) file is set to a `-dev-` version.
2. Run `./gradlew clean bintrayUpload`.
