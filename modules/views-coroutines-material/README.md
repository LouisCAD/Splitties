# Views Coroutines Material

*Material Components + Kotlin coroutines.*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.viewsCoroutinesMaterial`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-views-coroutines-material`.

## Content

Extension functions:

| **Name** | **Description**
| -------- | ---------------
| `FloatingActionButton.showAndAwaitOneClickThenHide` | Calls `show()`, suspends until the FAB is clicked and finally calls `hide()`. Cancellable.
