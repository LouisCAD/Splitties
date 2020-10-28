# Releasing this library:

## Releasing a stable, beta or alpha version

Run the interactive script

Run [Releasing.kts](Releasing.kts) (preferably in system terminal as IDE could crash)
with `kotlinc -script Releasing.kts` and follow the steps directly from the command line.

## Publishing a dev version

1. Make sure `splitties.version` in the [libraries_version.txt](libraries_version.txt) file is
set to a new `-dev-` version.
2. Commit "Dev version X.X.X-dev-XXX"
3. Push the commit and wait for the GitHub Action to run the release (the automated workflow should
take less than an hour).
