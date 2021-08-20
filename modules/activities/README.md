# Activities

*Start activities with minimal boilerplate.*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.activities`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-activities`.

## Content

### Starting Activities

The `start` extension function for `Context` takes advantage of reified type
parameters to allow you to write such code: `start<AboutActivity>()`.

There's an optional lambda where the `Intent` is the receiver so you can
edit it (e.g. adding flags) before the activity is started with it.

The `startActivity` extension function for `Context` is designed for implicit
intents. It expects the `Intent` action as first parameter, and takes an optional
lambda to edit the intent further, like `start`.
