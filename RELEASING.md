# Releasing this library:

## Releasing a stable, beta or alpha version

1. Checkout the `develop` branch, if not already done.
2. Change the `library_version` ext property in root project's `build.gradle`
file to a non-SNAPSHOT version.
3. Update the `README.md` with the new version.
4. Run `git commit -am "Prepare for release X.Y.Z"`
(where X.Y.Z is the new version).
5. `git tag -a vX.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version).
6. Run `./gradlew clean bintrayUpload`.
7. Run `git push origin`.
8. Create a pull request from the `develop` to the `master` branch on GitHub for the new version, if not already done.
9. Sign in on Bintray and publish the packages.
10. Run `git push origin --tags`.
11. Merge the pull request for the new version on GitHub.
12. Publish release on GitHub.
13. Checkout the `master` branch.
14. Pull from GitHub repository to update the local `master` branch.
15. Checkout the `develop` branch.
16. Change the `library_version` ext property in root project back to a
-SNAPSHOT version.
17. Run `git commit -am "Prepare next development version."`.
18. Run `git push origin`.

## Publishing a SNAPSHOT

1. Make sure `library_version` ext property in root project's `build.gradle`
file is set to a -SNAPSHOT version.
2. Run `./gradlew artifactoryPublish`. **Do it from command line,
not from Android Studio**, as the artifacts are not uploaded when the
gradle task is run from the IDE (as of Android Studio `3.1.0-rc01`) because
of a yet to be reported bug.
