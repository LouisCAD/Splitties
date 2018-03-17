# Releasing this library:

1. Change the `library_version` ext property in root project to a non-SNAPSHOT
version.
2. Update the `README.md` with the new version.
3. Run `git commit -am "Prepare for release X.Y.Z"`
(where X.Y.Z is the new version).
4. Run `./gradlew clean bintrayUpload`.
5. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version).
6. Run `git push origin`.
7. Sign in on Bintray and publish the packages.
8. Publish release on GitHub.
6. Change the `library_version` ext property in root project back to a
-SNAPSHOT version.
7. Run `git commit -am "Prepare next development version."`.
8. Run `git push origin && git push origin --tags`.
