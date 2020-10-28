# Collections

*`forEach` for `List`s without `Iterator` allocation.*

Supported platforms: **Linux** (x64), **MingW (x64)**, **macOS** (x64), **iOS** (arm32, arm64 & x64), **JS**, **JVM** (including Android).

## Content

The `forEachByIndex` and `forEachWithIndex` extension functions for `List`
allows you to iterate on a `List` without allocating an `Iterator`.

This is useful when you need to iterate on a `List` in performance critical
conditions (e.g. code executed/called from an `onDraw` method, or code run
on the UI thread more generally).

You also have the reverse equivalents: `forEachReversedByIndex` and
`forEachReversedWithIndex`.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-collections:{{version.splitties3}}")
```
