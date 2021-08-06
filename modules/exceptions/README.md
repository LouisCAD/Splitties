# Exceptions

*`unexpectedValue(…)`, `unsupportedAction(…)` and similar functions that return `Nothing`.*

Supported platforms: **Android**.

## Setup

This dependency is not included in any of the [fun-packs](../../README.md#download),
because we don't believe all apps need its content.

Add it with [refreshVersions](https://github.com/jmfayard/refreshVersions):
`Splitties.exceptions`.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-exceptions`.

## Content

Here's the list of the included functions:
* `unexpectedValue(…)` (puts the value in the `IllegalStateException` message)
* `illegalArg(…)` (shortcut to `throw IllegalArgumentException(…)`)
* `unsupported(…)` (shortcut to `throw UnsupportedOperationException(…)`)
* `unsupportedAction(…)` (designed for unsupported `Intent` actions)
