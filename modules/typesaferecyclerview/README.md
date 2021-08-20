# Typesafe RecyclerView

*Typesafe `ViewHolder` and `ItemViewHolder` for easy basic usage of
`RecyclerView`.*

Supported platforms: **Android**.

This module consists of two `ViewHolder` subclasses that make it typesafe,
and easier to use for the common use case which is to bind a `ViewHolder` to a
POJO (plain-old Java `Object`) or a POKA (plain-old Kotlin `Any`).
See the sample to understand how it works.

## Setup

This dependency is not included in any of the [fun-packs](../../README.md#download),
because we don't believe all apps need its content.

Add it with [refreshVersions](https://github.com/jmfayard/refreshVersions):
`Splitties.typesaferecyclerview`.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-typesaferecyclerview`.

### Usage

When using `ViewHolder`, use the one from Splitties to get the typesafe one.

See it in action in the sample: [DemoAdapter](
../../samples/android-app/src/androidMain/kotlin/com/example/splitties/demo/DemoAdapter.kt
).
