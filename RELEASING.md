# Releasing this library:

## Releasing a stable, beta or alpha version

Run the interactive script

Run [Releasing.kts](Releasing.kts) (preferably in system terminal as IDE could crash)
with `kotlinc -script Releasing.kts` and follow the steps directly from the command line.

## Publishing a dev version

1. Make sure `splitties.version` in the [libraries_version.properties](libraries_version.properties) file is
set to a `-dev-` version.
2. Run `./gradlew clean bintrayUpload`.
<!-- TODO: The step above is obsolete is set to be replaced by a GitHub Action -->
