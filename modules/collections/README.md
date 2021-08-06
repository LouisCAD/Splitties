# Collections

*`forEach` for `List`s without `Iterator` allocation.*

Supported platforms: **Linux** (x64), **MingW (x64)**, **macOS** (x64), **iOS** (arm32, arm64 & x64), **JS**, **JVM** (including Android).

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.collections`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-collections`.

## Content

The `forEachByIndex` and `forEachWithIndex` extension functions for `List`
allows you to iterate on a `List` without allocating an `Iterator`.

This is useful when you need to iterate on a `List` in performance critical
conditions (e.g. code executed/called from an `onDraw` method, or code run
on the UI thread more generally).

You also have the reverse equivalents: `forEachReversedByIndex` and
`forEachReversedWithIndex`.
