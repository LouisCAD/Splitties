# Dimensions

*Android `dp` extensions for `View` and `Context`. Particularly handy
when using [Views DSL](../views-dsl/README.md).*

`dip` and `dp` extension functions on `View` and `Context` take an `Int`
parameter and return the value in Android's dp. The former (dip) returns an
`Int` while the latter (dp) returns a `Float`.

## Examples

```kotlin
val minTapTargetSize = dip(48)
```

```kotlin
val circleDiameter = dp(48)
```

## Download

```groovy
implementation("com.louiscad.splitties:splitties-dimensions:$splitties_version")
```
