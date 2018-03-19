# Releasing this library:

## Releasing a stable, beta or alpha version

1. Change the `library_version` ext property in root project's `build.gradle`
file to a non-SNAPSHOT version.
2. Update the `README.md` with the new version.
3. Run `git commit -am "Prepare for release X.Y.Z"`
(where X.Y.Z is the new version).
4. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version).
5. Run `./gradlew clean bintrayUpload`.
6. Run `git push origin`.
7. Sign in on Bintray and publish the packages.
8. Publish release on GitHub.
6. Change the `library_version` ext property in root project back to a
-SNAPSHOT version.
7. Run `git commit -am "Prepare next development version."`.
8. Run `git push origin && git push origin --tags`.

## Publishing a SNAPSHOT

1. Make sure `library_version` ext property in root project's `build.gradle`
file is set to a -SNAPSHOT version.
2. Run `./gradlew artifactoryPublish`. **Do it from command line,
not from Android Studio**, as the artifacts are not uploaded when the
gradle task is run from the IDE (as of Android Studio `3.1.0-rc01`) because
of a yet to be reported bug.
