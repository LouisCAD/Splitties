# Releasing this library:

## Releasing a stable, beta or alpha version

Run the interactive script

Run [Releasing.kts](Releasing.main.kts) (preferably in system terminal as IDE could crash)
with `kotlinc -script Releasing.main.kts` and follow the steps directly from the command line.

## Publishing a SNAPSHOT version

1. Make sure the content of the [libraries_version.txt](libraries_version.txt) file is
set to a new `-SMAPSHOT` version on the target branch (on GitHub).
2. Go to [this url](https://github.com/LouisCAD/Splitties/actions/workflows/publish-to-sonatype-snapshots.yml)
3. Click the "Run workflow" white button, select the target branch, and click the green "Run workflow" button
4. Wait (if you want) until the Workflow run completes.
