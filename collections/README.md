# Collections

*`forEachByIndex` to iterate on `List`s without allocating an `Iterator`.*

## Content

The `forEachByIndex` and `forEachWithIndex` extension functions for `List`
allows you to iterate on a `List` without allocating an `Iterator`.

This is useful when you need to iterate on a `List` in performance critical
conditions (e.g. code executed/called from an `onDraw` method, or code run
on the UI thread more generally).
