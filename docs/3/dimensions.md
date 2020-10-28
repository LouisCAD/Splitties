# Dimensions

*Android `dp` extensions for `View` and `Context`. Particularly handy
when using [Views DSL](../views-dsl/README.md).*

Supported platforms: **Android**.

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

## Download

```groovy
implementation("com.louiscad.splitties:splitties-dimensions:$splitties_version")
```
