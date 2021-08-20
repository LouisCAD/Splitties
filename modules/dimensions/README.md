# Dimensions

*Android `dp` extensions for `View` and `Context`. Particularly handy
when using [Views DSL](../views-dsl/README.md).*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.dimensions`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-dimensions`.

The `dp` and `dip` extensions functions for `View` and `Context` are exactly the same,
use the naming you prefer. They convert a value in Android's `dp` to pixels for use in code.

The overload taking an `Int` returns an `Int`, the one taking a `Float` returns a `Float`.

## Examples

```kotlin
val minTapTargetSize = dip(48)
```

```kotlin
val circleDiameter = dp(48f)
```
